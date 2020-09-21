package com.selvakumarsm.elasticmodule2

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout

abstract class ElasticViewOrchestrator : MotionLayout {

    companion object {
        private const val TAG = "FlexibleView"
    }

    // Supported transitions when a new view is added to this layout.
    enum class TransitionState {
        EXPAND_AND_COLLAPSE_CHILD_VIEWS, // Adds the new view in expanded state and collapses rest of the views
        JUST_EXPAND // Adds the new view in expanded state and does not change the state of other views
    }

    class LayoutConfig(
        val viewGapInDp: Int = 20,
        val transitionState: TransitionState = TransitionState.EXPAND_AND_COLLAPSE_CHILD_VIEWS
    )

    interface ViewStateChangeListener {
        fun onExpanded(view: View)
        fun onCollapsed(view: View)
        fun onStateTransitionInProgress(view: View)
        fun onViewAdded(view: View)
        fun onViewRemoved(view: View)
        // TODO - Add callbacks for view removed/added
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

        Log.d(TAG, "addView: ${child.id}")
        // TODO - might want to get the params directly from child view
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

    protected abstract fun getChildLayoutParams(): ViewGroup.LayoutParams

    protected abstract fun applyConstraint(child: View)

    fun expandView(view: ElasticView) = view.expand()

    fun collapseView(view: ElasticView) = view.collapse()

    fun toggleViewState(view: ElasticView) = view.toggle()

    private fun expandOrCollapseViews(view: View) {
        when (layoutConfig.transitionState) {
            TransitionState.JUST_EXPAND -> {
                expandView(view as ElasticView)
            }
            TransitionState.EXPAND_AND_COLLAPSE_CHILD_VIEWS -> {
                collapseAllChildViews()
                //TODO wait till all childs are collapsed and then expand this view. Use Transition Listeners attached in the child view.
                expandView(view as ElasticView)
            }
        }
    }

    private fun collapseAllChildViews() {
        (0 until childCount - 1).forEach { index ->
            collapseView(getChildAt(index) as ElasticView)
            Log.d(TAG, "collapseAllChildViews: Collapsed $index")
        }
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