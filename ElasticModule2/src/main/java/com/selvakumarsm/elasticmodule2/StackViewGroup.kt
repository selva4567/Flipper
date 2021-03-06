package com.selvakumarsm.elasticmodule2

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.constraintlayout.motion.widget.TransitionBuilder
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.children
import kotlinx.coroutines.*
import kotlin.coroutines.resume

/**
 * Container class for collapsable/expandable views. Views that gets added into this container
 * must implement the interface [ElasticProperties]
 *
 * @property layoutManager is mandatory for positioning the children of this container.
 *
 * Include this view into your layout file and start adding views to it. Depending on what type of
 * @property layoutManager you pass, child views gets positioned.
 */
class StackViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    MotionLayout(context, attrs, defStyleAttr) {

    private lateinit var containerTransition: MotionScene.Transition
    var containerViewStateChangeListener: ContainerViewStateChangeListener? = null
    var layoutManager: LayoutManager? = null

    init {
        createTransition()
    }

    override fun addView(view: View) {
        val layoutParams = LayoutParams(MATCH_CONSTRAINT, MATCH_CONSTRAINT)
        addView(view, layoutParams)
    }

    override fun addView(child: View?, index: Int) {
        throw IllegalArgumentException("Use addView(view) instead")
    }

    override fun addView(child: View?, width: Int, height: Int) {
        throw IllegalArgumentException("Use addView(view) instead")
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        if (child !is ElasticProperties)
            throw IllegalArgumentException("Cannot add views which doesn't implement ElasticProperties")
        if (layoutManager == null)
            throw IllegalStateException("Set LayoutManager first before adding any views")
        child.id = if (child.id == -1) View.generateViewId() else child.id
        Log.d(TAG, "addView: ViewId ${child.id}")
        super.addView(child, params)
        Log.d(TAG, "addMotionView: Total child count $childCount")
        addViewWithAnimation(child)
    }

    fun removeViewsOnTopOf(view: View) {
        var childIndex = indexOfChild(view)

        if (childIndex == -1) {
            Log.d(TAG, "removeViewsOnTopOf: No views found in the stack with id ${view.id}")
            return
        }
        Log.d(TAG, "removeViewsOnTopOf: Removing from from ${childIndex + 1}")
        val startScene = getConstraintSet(containerTransition.startConstraintSetId)
        startScene.clone(this)
        val endScene = getConstraintSet(containerTransition.endConstraintSetId)
        endScene.clone(this)
        val childrenToRemove = mutableListOf<View>()
        (childIndex + 1 until childCount).forEach {
            val child = getChildAt(it)
           layoutManager!!.removedViewPosition(child, children, endScene)
            childrenToRemove.add(child)
        }
        endScene.applyTo(this)

        setTransition(
            containerTransition.startConstraintSetId,
            containerTransition.endConstraintSetId
        )
        //TODO - Make this configurable
        setTransitionDuration(1000)
        addTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (currentId == containerTransition.endConstraintSetId) {
                    removeTransitionListener(this)
                    Log.d(TAG, "removeViewsOnTopOf: Removed views.")
                    containerViewStateChangeListener?.onViewVisible(view)
                    childrenToRemove.forEach {
                        removeView(it)
                    }
                    containerViewStateChangeListener?.onViewsRemoved(childrenToRemove)
                }
            }
        })
        transitionToEnd()
    }


    fun pop() {
        if (childCount > 0) {
            val child = getChildAt(childCount - 1)
            Log.d(TAG, "pop: Removing child at position ${childCount - 1}, id ${child.id}")
            removeViewWithAnimation(child, true)
        } else {
            Log.d(TAG, "pop: No child is present to pop.")
        }
    }

    fun canPop(): Boolean = childCount > 0

    private fun addViewWithAnimation(view: View) {
        val startScene = getConstraintSet(containerTransition.startConstraintSetId)
        startScene.clone(this)
        layoutManager!!.startLayoutPosition(view, children, startScene)
        startScene.applyTo(this)

        val endScene = getConstraintSet(containerTransition.endConstraintSetId)
        endScene.clone(this)
        layoutManager!!.endLayoutPosition(view, children, endScene)
        endScene.applyTo(this)

        setTransition(
            containerTransition.startConstraintSetId,
            containerTransition.endConstraintSetId
        )
        //TODO - Make this configurable
        setTransitionDuration(1000)

        addTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                removeTransitionListener(this);
                containerViewStateChangeListener?.onViewAdded(view)
            }
        })
        transitionToEnd()
    }


    private fun removeViewWithAnimation(view: View, removeView: Boolean) {
        val startScene = getConstraintSet(containerTransition.startConstraintSetId)
        startScene.clone(this)

        val endScene = getConstraintSet(containerTransition.endConstraintSetId)
        endScene.clone(this)
        layoutManager!!.removedViewPosition(view, children, endScene)
        endScene.applyTo(this)

        setTransition(
            containerTransition.startConstraintSetId,
            containerTransition.endConstraintSetId
        )
        //TODO - Make this configurable
        setTransitionDuration(1000)

        GlobalScope.launch(Dispatchers.Main) {
            awaitTransitionToEnd()
            val prevChildIndex = indexOfChild(view) - 1
            if (prevChildIndex >= 0)
                containerViewStateChangeListener?.onViewVisible(getChildAt(prevChildIndex))
            Log.d(TAG, "removeViewWithAnimation: View animated to end.")
            if (removeView) {
                super.removeView(view)
                containerViewStateChangeListener?.onViewsRemoved(listOf(view))
            }
        }
    }

    override fun removeView(view: View?) {
        Log.d(TAG, "removeView: Removing view ${view?.id}")
        super.removeView(view)
    }

    private suspend fun awaitTransitionToEnd() {
        transitionToEnd()
        var transitionEndListener: MotionLayout.TransitionListener? = null
        try {
            withTimeout(3000) {
                suspendCancellableCoroutine<Unit> {
                    val transitionEndListener = object : TransitionAdapter() {
                        override fun onTransitionCompleted(
                            motionLayout: MotionLayout?,
                            currentId: Int
                        ) {
                            if (currentId == containerTransition.endConstraintSetId) {
                                Log.d(TAG, "onTransitionCompleted: $currentId")
                                it.resume(Unit);
                                removeTransitionListener(this)
                            }
                        }

                    }
                    it.invokeOnCancellation {
                        removeTransitionListener(transitionEndListener)
                    }
                    addTransitionListener(transitionEndListener)
                }
            }
        } catch (ex: TimeoutCancellationException) {
            Log.d(TAG, "awaitTransitionToEnd: Transition did not complete in 3s")
            transitionEndListener?.let {
                removeTransitionListener(it)
            }
        }
    }

    private fun createTransition() {
        val scene = MotionScene(this)

        val startId = View.generateViewId()
        val startScene = ConstraintSet()
        startScene.clone(this)
        val endId = View.generateViewId()
        val endScene = ConstraintSet()
        endScene.clone(this)
        val transitionId = View.generateViewId()

        Log.d(TAG, "createTransition: startId $startId, endId $endId, transitionId $transitionId")

        containerTransition = TransitionBuilder.buildTransition(
            scene,
            transitionId,
            startId,
            startScene,
            endId,
            endScene
        )
        containerTransition.duration = 3000
        scene.addTransition(containerTransition)
        scene.setTransition(containerTransition)
        setScene(scene)
    }


    interface ContainerViewStateChangeListener {
        /**
         * Gets called when a new view is added completely(after animation ends)
         */
        fun onViewAdded(view: View)

        /**
         * Gets called when a view/views are removed completely(after animation ends)
         */
        fun onViewsRemoved(views: List<View>)

        /**
         * Not Yet Supported
         */
        fun onViewsHidden(views: List<View>)

        /**
         * Gets called when view becomes visible again. It happens when any of the top views in the
         * stack gets removed and this view becomes top view. This won't be called when a view is
         * added to the stack.
         */
        fun onViewVisible(view: View)
    }

    companion object {
        private val TAG = StackViewGroup::class.simpleName
    }
}