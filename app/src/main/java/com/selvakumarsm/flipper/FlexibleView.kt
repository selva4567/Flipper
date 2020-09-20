package com.selvakumarsm.flipper

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout

abstract class FlexibleView : MotionLayout {

    companion object {
        private const val TAG = "FlexibleView"
    }

    class LayoutConfig(val viewGapInDp: Int = 20, val viewMode: ViewMode = ViewMode.EXPANDED) {

        enum class ViewMode {
            EXPANDED, COLLAPSED, EXPAND_AND_COLLAPSE_CHILD_VIEWS
        }
    }

    //Field for gap between views (weight) - FloatArray
    //Field for transition - MotionLayout.Transition

    private val childIndex = ArrayList<Int>()
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
        if (child == null || child !is MotionLayout)
            throw IllegalArgumentException("View to be added must be a MotionLayout")

        Log.d(TAG, "addView: ${child.id}")
        child.layoutParams = getChildLayoutParams()
        super.addView(child)
        expandOrCollapseViews(child)
        applyConstraint(child)
    }

    override fun onViewAdded(view: View?) {
        super.onViewAdded(view)
        Log.d(TAG, "onViewAdded: ${view?.id}")
    }


    abstract fun expandView(view: View)

    abstract fun collapseView(view: View)

    protected abstract fun getChildLayoutParams(): ViewGroup.LayoutParams

    protected abstract fun applyConstraint(child: View)

    private fun expandOrCollapseViews(view: View) {
        when (layoutConfig.viewMode) {
            LayoutConfig.ViewMode.EXPANDED -> {

            }
            LayoutConfig.ViewMode.COLLAPSED -> {

            }
            LayoutConfig.ViewMode.EXPAND_AND_COLLAPSE_CHILD_VIEWS -> {
                collapseAllChildViews()
                expandView(view)
            }
        }
    }

    private fun collapseAllChildViews() {
        (0 until childCount).forEach {childIndex ->
            collapseView(getChildAt(childIndex))
            Log.d(TAG, "collapseAllChildViews: Collapsed $childIndex")
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