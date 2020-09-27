package com.selvakumarsm.flipper.explore.view

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.motion.widget.TransitionBuilder
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginTop
import com.selvakumarsm.elasticmodule2.ElasticVerticalView

class ElasticViewContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    MotionLayout(context, attrs, defStyleAttr) {

    private var containerTransition: MotionScene.Transition? = null

    init {
        createTransition()
    }

    override fun onFinishInflate() {
        Log.d(TAG, "onFinishInflate: ")
        super.onFinishInflate()
    }

    fun addMotionView(view: View) {
        view.id = View.generateViewId()
        val layoutParams = LayoutParams(MATCH_PARENT, fromDp(context, 200)).also {
            it.topMargin = fromDp(context, 20)
        }
        addView(view, layoutParams)
        Log.d(TAG, "addMotionView: Total child count $childCount")
        applyConstraint(view)
    }

    private fun applyConstraint(child: View) {
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
        getConstraintSet(containerTransition!!.startConstraintSetId).also { it.clone(this) }
        setTransition(
            containerTransition!!.startConstraintSetId,
            containerTransition!!.endConstraintSetId
        )
    }

    private fun createTransition() {
        val scene = MotionScene(this)

        val startId = View.generateViewId()
        val startScene = ConstraintSet()
        startScene.clone(this)
        val endId = View.generateViewId()
        val endScene = ConstraintSet()
        endScene.clone(this)
        val transitionId = View.generateViewId()

        Log.d(TAG, "createTransition: startId $startId, endId $endId, transitionId $transitionId")

        containerTransition = TransitionBuilder.buildTransition(
            scene,
            transitionId,
            startId,
            startScene,
            endId,
            endScene
        )

        scene.addTransition(containerTransition)
        scene.setTransition(containerTransition)
        setScene(scene)
    }

    private fun fromDp(context: Context, inDp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (inDp * scale).toInt()
    }


    companion object {
        private val TAG = ElasticViewContainer::class.simpleName
    }
}