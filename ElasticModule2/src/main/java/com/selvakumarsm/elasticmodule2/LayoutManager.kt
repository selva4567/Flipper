package com.selvakumarsm.elasticmodule2

import android.content.Context
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

/**
 * Interface which defines how to position the children in the [StackViewGroup]. You have to
 * implement this interface in your custom class say GridLayoutManager and override
 * @see applyConstraint to position its children in grid manner using [ConstraintLayout]
 */
interface LayoutManager {
    fun startLayoutPosition(view:View, children: Sequence<View>, startConstraint: ConstraintSet)
    fun endLayoutPosition(view:View, children: Sequence<View>, endConstraint: ConstraintSet)
}