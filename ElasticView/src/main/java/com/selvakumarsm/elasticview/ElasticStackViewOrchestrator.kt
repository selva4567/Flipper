package com.selvakumarsm.elasticview

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.widget.ConstraintSet

class ElasticStackViewOrchestrator : BaseElasticViewOrchestrator {
    companion object {
        private const val TAG = "FlexibleStackView"
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun getChildLayoutParams(): ViewGroup.LayoutParams = LayoutParams(
        MATCH_PARENT,
        MATCH_PARENT
    )

    override fun applyConstraint(child: View) {
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
        constraintSet.connect(
            child.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.applyTo(this)
    }

}