package com.selvakumarsm.flipper.explore.view

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.motion.widget.TransitionBuilder
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumesAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ElasticViewContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    MotionLayout(context, attrs, defStyleAttr) {

    private var containerTransition: MotionScene.Transition? = null

    init {
        createTransition()
    }

    override fun onFinishInflate() {
        Log.d(TAG, "onFinishInflate: ")
        super.onFinishInflate()
    }

    fun addMotionView(view: View) {
        view.id = View.generateViewId()
        val layoutParams = LayoutParams(MATCH_PARENT, fromDp(context, 200)).also {
            it.topMargin = fromDp(context, 20)
        }
        view.setOnClickListener {
            Log.d(TAG, "addMotionView: VIew ${view.id} clicked")
            toggle(it)
        }
        addView(view, layoutParams)
        Log.d(TAG, "addMotionView: Total child count $childCount")
        applyConstraint(view)
    }

    private fun toggle(view: View) {
        when (currentState) {
            containerTransition!!.startConstraintSetId -> {
                val startScene = getConstraintSet(containerTransition!!.startConstraintSetId)
                startScene.clone(this)
                val endScene = getConstraintSet(containerTransition!!.endConstraintSetId)
                endScene.clone(this)
                endScene.constrainWidth(view.id, MATCH_PARENT)
                endScene.constrainHeight(view.id, WRAP_CONTENT)
                endScene.connect(
                    view.id,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    fromDp(context, 20)
                )
                endScene.connect(
                    view.id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP,
                    fromDp(context, 20)
                )
                endScene.connect(
                    view.id,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END,
                    fromDp(context, 20)
                )
                endScene.connect(
                    view.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM,
                    fromDp(context, 20)
                )
                setTransition(
                    containerTransition!!.startConstraintSetId,
                    containerTransition!!.endConstraintSetId
                )
                transitionToEnd()
            }
            containerTransition!!.endConstraintSetId -> {
//                applyConstraint(view)
                transitionToStart()
            }
            else -> {
                Log.w(TAG, "toggle: Unknown current state $currentState")
                return
            }
        }

    }

    private fun applyConstraint(child: View) {
        TransitionManager.beginDelayedTransition(this)
        val childBefore = if (childCount <= 1) null else getChildAt(childCount - 2)
        Log.d(TAG, "applyConstraint: aligning ${child.id} vertically to ${childBefore?.id}")
        val startScene = getConstraintSet(containerTransition!!.startConstraintSetId)
        startScene.clone(this)
        startScene.connect(
            child.id,
            ConstraintSet.TOP,
            childBefore?.id ?: ConstraintSet.PARENT_ID,
            if (childBefore == null) ConstraintSet.TOP else ConstraintSet.BOTTOM
        )
        startScene.connect(
            child.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        startScene.connect(
            child.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        startScene.applyTo(this)
        setTransition(
            containerTransition!!.startConstraintSetId,
            containerTransition!!.endConstraintSetId
        )
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

        scene.addTransition(containerTransition)
        scene.setTransition(containerTransition)
        setScene(scene)
    }

    private fun fromDp(context: Context, inDp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (inDp * scale).toInt()
    }


    companion object {
        private val TAG = ElasticViewContainer::class.simpleName
    }
}