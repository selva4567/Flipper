package com.selvakumarsm.flipper.loan.view

import android.content.Context
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.selvakumarsm.elasticmodule2.LayoutManager

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

}