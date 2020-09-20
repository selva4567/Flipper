package com.selvakumarsm.flipper.flexyview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import java.lang.IllegalArgumentException

class VerticalStackView : FlexibleView {

    companion object {
        private const val TAG = "VerticalStackView"
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    // TODO - override rest of the removeView overloaded methods as well
    override fun removeView(view: View?) {
        if (view == null || view !is MotionLayout)
            throw IllegalArgumentException("Cannot remove view if it is not MotionLayout")

        TransitionManager.beginDelayedTransition(this)
        constrainToViewAbove(view)
        super.removeView(view)
    }

    override fun getChildLayoutParams(): ViewGroup.LayoutParams =
        LayoutParams(MATCH_PARENT, WRAP_CONTENT)

    override fun applyConstraint(child: View) {
        TransitionManager.beginDelayedTransition(this)
        val childBefore = if (childCount <= 1) null else getChildAt(childCount - 2)
        Log.d(TAG, "applyConstraint: aligning ${child.id} vertically to ${childBefore?.id}")
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

    private fun constrainToViewAbove(view: View) {
        val viewIndex = indexOfChild(view)
        if (viewIndex == -1)
            throw IllegalArgumentException("View not found in the hierarchy")
        // Since it is a last child, no need to re-constrain. Because no child is constrained to it.
        if (isOnlyChild(viewIndex) || viewIndex == childCount - 1)
            return
        var constraintSet = ConstraintSet()
        constraintSet.clone(this)
        if (viewIndex == 0)
            constraintSet.connect(
                getChildAt(viewIndex + 1).id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
        else
            constraintSet.connect(
                getChildAt(viewIndex + 1).id,
                ConstraintSet.TOP,
                getChildAt(viewIndex - 1).id,
                ConstraintSet.BOTTOM
            )
        constraintSet.applyTo(this)
    }
}