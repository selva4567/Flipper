package com.selvakumarsm.flipper.explore.view

import android.content.Context
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.selvakumarsm.elasticmodule2.LayoutManager
import java.lang.IllegalArgumentException

// TODO - This class is incomplete. It works well only for first two child.
class HorizontalLayoutManager(
    private val gapInDp: Int = 10,
    private val layoutParams: ConstraintLayout.LayoutParams? = null
) : LayoutManager {

    override fun applyConstraint(
        child: View,
        rootView: MotionLayout,
        containerTransition: MotionScene.Transition
    ) {
        rootView.apply {
            TransitionManager.beginDelayedTransition(this)
            val startScene = getConstraintSet(containerTransition!!.startConstraintSetId)
            startScene.clone(this)
            if (childCount == 1) {
                startScene.also {
                    it.connect(
                        child.id,
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP
                    )
                    it.connect(
                        child.id,
                        ConstraintSet.START,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.START
                    )
                }
            } else if (childCount == 2) {
                startScene.also {
                    it.connect(
                        child.id,
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP
                    )
                    it.connect(
                        child.id,
                        ConstraintSet.END,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.END
                    )
                }
            } else {
                throw IllegalArgumentException("Cannot add more than two views.")
            }
            startScene.applyTo(this)
            setTransition(
                containerTransition!!.startConstraintSetId,
                containerTransition!!.endConstraintSetId
            )
        }
    }

    override fun getLayoutParams(context: Context): ConstraintLayout.LayoutParams {
        return layoutParams ?: ConstraintLayout.LayoutParams(
            fromDp(context, 180),
            fromDp(context, 230)
        ).also {
            it.topMargin = fromDp(context, gapInDp)
            it.bottomMargin = fromDp(context, gapInDp)
            it.marginEnd = fromDp(context, gapInDp)
            it.marginStart = fromDp(context, gapInDp)
        }
    }

    private fun fromDp(context: Context, inDp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (inDp * scale).toInt()
    }


    companion object {
        private val TAG = HorizontalLayoutManager::class.simpleName
    }
}