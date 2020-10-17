package com.selvakumarsm.elasticmodule2

import android.content.Context
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Interface which defines how to position the children in the [StackViewGroup]. You have to
 * implement this interface in your custom class say GridLayoutManager and override
 * @see applyConstraint to position its children in grid manner using [ConstraintLayout]
 */
interface LayoutManager {
    /**
     * Override this method to position the child view inside [StackViewGroup]
     *
     * @param child that needs to be constrained to the rest of the children in the [StackViewGroup]
     * @param rootView container view [StackViewGroup] using which you can retrieve the existing
     * children and constrain the new view to them or to the parent itself.
     * @param containerTransition used to retrieve and update the [ConstraintSet] for collapsed/expanded state
     * after applying the constraints to the view. Once the respective [ConstraintSet] is updated,
     * set the same to this containerTransition at the end.
     */
    fun applyConstraint(child: View, rootView: MotionLayout, containerTransition: MotionScene.Transition)

    fun removeConstraint(child: View)

    /**
     * Override this method to specify layoutparams which gets applied to every view in the
     * container [StackViewGroup]
     */
    fun getLayoutParams(context: Context): ConstraintLayout.LayoutParams
}