package com.selvakumarsm.elasticmodule2

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

/**
 * Interface which defines how to position the children in the [StackViewGroup]. You have to
 * implement this interface in your custom class say GridLayoutManager and override
 * @see applyConstraint to position its children in grid manner using [ConstraintLayout]
 */
interface LayoutManager {
    /**
     * It defines the origin position/state of the view before it is added to screen. From this position/state
     * animation starts while view is getting added to the screen.
     *
     * @param view to be added
     * @param children useful if the new view depends on the existing sibling views for relative positioning
     * @param startConstraint needs to be mutated with the starting position from where animation should start
     */
    fun startLayoutPosition(view: View, children: Sequence<View>, startConstraint: ConstraintSet)

    /**
     * It defines the final position/state of the view after it is rendered to screen. Animation ends at
     * this position/state.
     *
     * @param view to be added
     * @param children useful if the new view depends on the existing sibling views for relative positioning
     * @param startConstraint needs to be mutated with the end position to which animation should terminate
     */
    fun endLayoutPosition(view: View, children: Sequence<View>, endConstraint: ConstraintSet)

    /**
     * This is useful to define the exit animation path when view is removed/hidden from the screen.
     *
     * @param view to be removed
     * @param children useful if the new view depends on the existing sibling views for relative positioning
     * @param startConstraint needs to be mutated with the end position to which animation should terminate
     */
    fun removedViewPosition(view: View, children: Sequence<View>, endConstraint: ConstraintSet)
}