package com.selvakumarsm.flipper.flexyview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.selvakumarsm.flipper.ExpandableView.Companion.EXPANDED

abstract class FlexibleView : MotionLayout {

    companion object {
        private const val TAG = "FlexibleView"
    }

    // Supported behaviors when a new view is added to this layout.
    enum class ViewBehavior {
        EXPAND_AND_COLLAPSE_CHILD_VIEWS, // Adds the new view in expanded state and collapses rest of the views
        JUST_EXPAND // Adds the new view in expanded state and does not change the state of other views
    }

    class LayoutConfig(
        val viewGapInDp: Int = 20,
        val viewBehavior: ViewBehavior = ViewBehavior.EXPAND_AND_COLLAPSE_CHILD_VIEWS
    )

    //Field for gap between views (weight) - FloatArray
    //Field for transition - MotionLayout.Transition

    var layoutConfig = LayoutConfig()

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
        if (child == null || child !is CollapsableView)
            throw IllegalArgumentException("View to be added must be a CollapsableView")

        Log.d(TAG, "addView: ${child.id}")
        // TODO - might want to get the params directly from child view
        child.layoutParams = getChildLayoutParams()
        super.addView(child)
        expandOrCollapseViews(child)
        applyConstraint(child)
    }

    override fun onViewAdded(view: View?) {
        super.onViewAdded(view)
        Log.d(TAG, "onViewAdded: ${view?.id}")
    }

    protected abstract fun getChildLayoutParams(): ViewGroup.LayoutParams

    protected abstract fun applyConstraint(child: View)

    fun expandView(view: CollapsableView) = view.expand()

    fun collapseView(view: CollapsableView) = view.collapse()

    private fun expandOrCollapseViews(view: View) {
        when (layoutConfig.viewBehavior) {
            ViewBehavior.JUST_EXPAND -> {
                expandView(view as CollapsableView)
            }
            ViewBehavior.EXPAND_AND_COLLAPSE_CHILD_VIEWS -> {
                collapseAllChildViews()
                expandView(view as CollapsableView)
            }
        }
    }

    private fun collapseAllChildViews() {
        (0 until childCount).forEach { index ->
            (getChildAt(index) as CollapsableView).collapse()
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