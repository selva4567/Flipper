package com.selvakumarsm.elasticmodule2

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet

class StackLayoutManager(private val context: Context) : LayoutManager {

    override fun startLayoutPosition(view: View, children: Sequence<View>, startConstraint: ConstraintSet) {
        startConstraint.apply {
            connect(
                view.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
            )
            connect(
                view.id,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
            )
            connect(
                view.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
        }
    }

    override fun endLayoutPosition(view: View, children: Sequence<View>, endConstraint: ConstraintSet) {
        endConstraint.apply {
            connect(
                view.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            connect(
                view.id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
        }
    }

    override fun removedViewPosition(
        view: View,
        children: Sequence<View>,
        endConstraint: ConstraintSet
    ) {
        endConstraint.apply {
            connect(
                view.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
            )
            connect(
                view.id,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
            )
            connect(
                view.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            clear(view.id, ConstraintSet.BOTTOM)
        }
    }

}