package com.selvakumarsm.elasticmodule2

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.motion.widget.TransitionBuilder
import androidx.constraintlayout.widget.ConstraintSet
import com.selvakumarsm.elasticmodule2.ElasticProperties
import com.selvakumarsm.elasticmodule2.ElasticView
import com.selvakumarsm.elasticmodule2.StateChangeListener
import java.lang.IllegalArgumentException

/**
 * Container class for collapsable/expandable views. Views that gets added into this container
 * must implement the interface [ElasticProperties]
 *
 * @property layoutManager is mandatory for positioning the children of this container.
 *
 * Include this view into your layout file and start adding views to it. Depending on what type of
 * layoutMangater you pass, child views gets positioned.
 */
class ElasticViewContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    MotionLayout(context, attrs, defStyleAttr) {

    private var containerTransition: MotionScene.Transition? = null
    var layoutManager: LayoutManager? = null

    init {
        createTransition()
    }

    override fun addView(view: View) {
        if (view !is ElasticProperties)
            throw IllegalArgumentException("Cannot add views which doesn't implement ElasticProperties")
        view.id = View.generateViewId()
        setupListeners(view)
        super.addView(view, layoutManager?.getLayoutParams(context))
        Log.d(TAG, "addMotionView: Total child count $childCount")
        layoutManager?.applyConstraint(view, this, containerTransition!!)
    }

    private fun setupListeners(view: View) {
        if (view !is ElasticProperties)
            throw IllegalArgumentException("Cannot add views which doesn't implement ElasticProperties")
        view.setOnClickListener {
            Log.d(TAG, "addMotionView: VIew ${view.id} clicked")
            toggle(it)
            view.toggle()
        }
        view.setStateChangeListener(object : StateChangeListener {
            override fun postCollapse(view: ElasticView) {
                view.elevation = 0f
            }

            override fun postExpand(view: ElasticView) {
            }

            override fun preCollapse(view: ElasticView) {
            }

            override fun preExpand(view: ElasticView) {
                view.elevation = 1f
            }
        })
    }

    private fun toggle(view: View) {
        when (currentState) {
            containerTransition!!.startConstraintSetId -> {
                getConstraintSet(containerTransition!!.startConstraintSetId).also {
                    it.clone(this)
                }
                getConstraintSet(containerTransition!!.endConstraintSetId).also {
                    it.clone(this)
                    it.constrainWidth(view.id, MATCH_PARENT)
                    it.constrainHeight(view.id, MATCH_PARENT)
                    constrainToParent(view.id, it)
                }
                setTransition(
                    containerTransition!!.startConstraintSetId,
                    containerTransition!!.endConstraintSetId
                )
                transitionToEnd()
            }
            containerTransition!!.endConstraintSetId -> {
                transitionToStart()
            }
            else -> {
                Log.w(TAG, "toggle: Unknown current state $currentState")
                return
            }
        }

    }

    private fun constrainToParent(id: Int, scene: ConstraintSet) = scene.apply {
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

    companion object {
        private val TAG = ElasticViewContainer::class.simpleName
    }
}