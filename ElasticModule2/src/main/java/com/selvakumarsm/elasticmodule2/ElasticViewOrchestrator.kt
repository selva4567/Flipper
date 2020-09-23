package com.selvakumarsm.elasticmodule2

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet

abstract class ElasticViewOrchestrator : MotionLayout {

    companion object {
        private const val TAG = "ElasticViewOrchestrator"
    }

    // Supported transitions when a new view is added to this layout.
    enum class TransitionState {
        FULL_EXPAND_AND_COLLAPSE_CHILD_VIEWS, // Adds the new view to expand to cover full screen and collapses rest of the views
        EXPAND_AND_COLLAPSE_CHILD_VIEWS, // Adds the new view in expanded mode to fit its contents and collapses rest of the views
        JUST_EXPAND, // Adds the new view in expanded state and does not change the state of other views
        JUST_COLLAPSE // Adds the new view in expanded state and does not change the state of other views
    }

    class LayoutConfig(
        val viewGapInDp: Int = 20,
        val expandToFullScreen: Boolean = false,
        val transitionState: TransitionState = TransitionState.JUST_COLLAPSE
    )

    interface ViewStateChangeListener {
        fun onExpanded(view: View)
        fun onCollapsed(view: View)
        fun onStateTransitionInProgress(view: View)
        fun onViewAdded(view: View)
        fun onViewRemoved(view: View)
    }

    var layoutConfig = LayoutConfig()
    var viewStateChangeListener: ViewStateChangeListener? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    // Gets called after inflating this view. This will not be called for every child addition/removal
    override fun onFinishInflate() {
        super.onFinishInflate()
        Log.d(TAG, "onFinishInflate: ")
    }

    override fun addView(child: View?) {
        if (child == null || child !is ElasticView)
            throw IllegalArgumentException("View to be added must be a CollapsableView")

        child.id = View.generateViewId()
        Log.d(TAG, "addView: ${child.id}")
        child.layoutParams = getChildLayoutParams()
        child.setOnClickListener { toggleViewState(child) }
        child.setTransitionListener(object : TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                // TODO - might want to provide update on this to the listeners
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                // TODO - might want to provide update on this to the listeners
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                val child = p0 as ElasticView
                Log.d(TAG, "onTransitionCompleted: ${child.id} $p1 ${child.state}")
                when (child.state) {
                    ElasticView.State.EXPANDED -> viewStateChangeListener?.onExpanded(p0!!)
                    ElasticView.State.COLLAPSED -> viewStateChangeListener?.onCollapsed(p0!!)
                    else -> Log.w(
                        TAG,
                        "onTransitionCompleted: Unknown transaction state change for view ${p0?.id}, transaction state $p1"
                    )
                }
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                // TODO - might want to provide update on this to the listeners
            }

        })
        super.addView(child)
        expandOrCollapseViews(child)
        applyConstraint(child)
        viewStateChangeListener?.onViewAdded(child)
    }

    //TODO - make this view specific. Let each child decide their layout params.
    protected abstract fun getChildLayoutParams(): ViewGroup.LayoutParams

    protected abstract fun applyConstraint(child: View)

    private fun expandView(view: ElasticView) {
        if (layoutConfig.expandToFullScreen) {
            Log.d(TAG, "expandView: To full screen")
            view.layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
            constrainToFullScreen(view)
        }
        view.expand()
    }

    private fun collapseView(view: ElasticView) {
        view.layoutParams = getChildLayoutParams()
        constrainToOriginalPosition(view)
        view.collapse()
    }

    private fun toggleViewState(view: ElasticView) {
        if (view.state == ElasticView.State.EXPANDED)
            collapseView(view)
        else
            expandView(view)
    }

    private fun expandOrCollapseViews(view: ElasticView) {
        when (layoutConfig.transitionState) {
            TransitionState.JUST_EXPAND -> {
                view.expand()
            }
            TransitionState.FULL_EXPAND_AND_COLLAPSE_CHILD_VIEWS -> {
                collapseAllChildViews()
                //TODO wait till all childs are collapsed and then expand this view. Use Transition Listeners attached in the child view.
                expandView(view)
            }
            TransitionState.JUST_COLLAPSE -> {
                view.collapse()
            }
        }
    }

    private fun collapseAllChildViews() {
        (0 until childCount - 1).forEach { index ->
            (getChildAt(index) as ElasticView).collapse()
            Log.d(TAG, "collapseAllChildViews: Collapsed $index")
        }
    }

    private fun constrainToFullScreen(child: MotionLayout) {
        TransitionManager.beginDelayedTransition(this)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(
            child.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            child.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            child.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.connect(
            child.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(this)
    }

    private fun constrainToOriginalPosition(child: ElasticView) {
        TransitionManager.beginDelayedTransition(this)
        val childIndex = indexOfChild(child)
        val childBefore = if (childIndex == 0) null else getChildAt(childIndex - 1)
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(
            child.id,
            ConstraintSet.TOP,
            childBefore?.id ?: ConstraintSet.PARENT_ID,
            if (childBefore == null) ConstraintSet.TOP else ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            child.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            child.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.applyTo(this)
    }

    /**
     * Get px from dp
     */
    protected fun fromDp(context: Context, inDp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (inDp * scale).toInt()
    }

    protected fun isOnlyChild(index: Int) = index == 0 && childCount == 1

}

