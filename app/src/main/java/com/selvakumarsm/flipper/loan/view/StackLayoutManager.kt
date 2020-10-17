package com.selvakumarsm.flipper.loan.view

import android.content.Context
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
import androidx.constraintlayout.widget.ConstraintSet
import com.selvakumarsm.elasticmodule2.LayoutManager

class StackLayoutManager(private val topOffsetInDp: Int = 100) : LayoutManager {

    override fun applyConstraint(
        child: View,
        rootView: MotionLayout,
        containerTransition: MotionScene.Transition
    ) {
        rootView.apply {
            val startScene = getConstraintSet(containerTransition!!.startConstraintSetId)
            startScene.clone(this)
            if (rootView.childCount > 1) {
                constrainToParentWithTopOffset(
                    child.id,
                    startScene,
                    0
                )
                startScene.clear(child.id, ConstraintSet.TOP)
                startScene.applyTo(this)

                val endScene = getConstraintSet(containerTransition!!.endConstraintSetId)
                endScene.clone(this)
                constrainToParentWithTopOffset(
                    child.id,
                    endScene,
                    fromDp(rootView.context, (rootView.childCount - 1) * topOffsetInDp)
                )
                endScene.applyTo(this)
                setTransition(
                    containerTransition!!.startConstraintSetId,
                    containerTransition!!.endConstraintSetId
                )
                transitionToEnd()
            }
            else {
                constrainToParentWithTopOffset(child.id, startScene, 0)
                startScene.applyTo(this)
                setTransition(
                    containerTransition!!.startConstraintSetId,
                    containerTransition!!.endConstraintSetId
                )
                transitionToStart()
            }
        }
    }

    override fun removeConstraint(child: View) {

    }

    override fun getLayoutParams(context: Context): ConstraintLayout.LayoutParams {
        return ConstraintLayout.LayoutParams(
            MATCH_CONSTRAINT,
            MATCH_CONSTRAINT
        )
    }

    private fun fromDp(context: Context, inDp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (inDp * scale).toInt()
    }

    private fun constrainToParentWithTopOffset(id: Int, scene: ConstraintSet, topOffsetInPx: Int) {
        scene.apply {
            connect(
                id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
            )
            connect(
                id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                topOffsetInPx
            )
            connect(
                id,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
            )
            connect(
                id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
            )
        }
    }

    companion object {
        private val TAG = StackLayoutManager::class.simpleName
    }

}