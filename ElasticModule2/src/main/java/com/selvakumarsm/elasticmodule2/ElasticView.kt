package com.selvakumarsm.elasticmodule2

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import com.selvakumarsm.elasticmodule2.ElasticProperties.State

/**
 *  Class which can expand/collapse on clicking on it. Based on the current state, view toggles
 *  between expanded/collapsed.
 *
 *  State change is achieved using [MotionLayout]. Using single layout file and two different
 *  [ConstraintSet] for the state definition, we are transitioning between states.
 *
 *  You can define initial state of the view [State.EXPANDED] or [State.COLLAPSED] while creating it
 *  in the layout file. By default, this view will render in [State.EXPANDED].
 *
 *  Right now only supported state transition duration is 300ms. It can not be overridden now.
 *
 *  @property collapseSceneId (Mandatory) - Id of [ConstraintSet] in the motion scene file which represents
 *  collapse state
 *
 *  @property expandSceneId  (Mandatory) - Id of [ConstraintSet] in the motion scene file which represents
 *  expand state
 *
 */
class ElasticView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    MotionLayout(context, attrs, defStyleAttr), ElasticProperties {

    /**
     * Holds current state of the view
     */
    private var state: State
    private var callback: ElasticStateChangeListener? = null
    private val expandSceneId: Int
    private val collapseSceneId: Int

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.ElasticView, 0, 0)
        collapseSceneId = arr.getResourceId(R.styleable.ElasticView_collapsedSceneId, -1)
        expandSceneId = arr.getResourceId(R.styleable.ElasticView_expandedSceneId, -1)
        state = when (arr.getInt(R.styleable.ElasticView_initialState, 0)) {
            0 -> State.COLLAPSED
            1 -> State.EXPANDED
            else -> {
                Log.d(TAG, "Invalid initialstate for view: ")
                State.EXPANDED
            }
        }
        Log.d(TAG, "Initial State set : $state")
        setupTransition()
        setupCallbacks()
    }

    private fun setupCallbacks() {
        setTransitionListener(object : TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {

            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                if (p1 == collapseSceneId)
                    callback?.postCollapse(p0 as ElasticView)
                else if (p1 == expandSceneId)
                    callback?.postExpand(p0 as ElasticView)
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

            }
        })
    }

    override fun setStateChangeListener(elasticStateChangeListener: ElasticStateChangeListener) {
        callback = elasticStateChangeListener
    }

    override fun expand() {
        if (expandSceneId == -1)
            throw IllegalStateException("Invalid expand scene id $expandSceneId")
        if (state == State.EXPANDED)
            return
        Log.d(TAG, "expand: $id")
        callback?.preExpand(this)
        transitionToState(expandSceneId)
        state = State.EXPANDED
    }

    override fun collapse() {
        if (collapseSceneId == -1)
            throw IllegalStateException("Invalid collapse scene id $collapseSceneId")
        if (state == State.COLLAPSED)
            return
        Log.d(TAG, "collapse: $id ")
        callback?.preCollapse(this)
        transitionToState(collapseSceneId)
        state = State.COLLAPSED
    }

    override fun toggle() {
        when (state) {
            State.COLLAPSED -> expand()
            State.EXPANDED -> collapse()
            State.IN_TRANSITION -> Log.d(TAG, "toggle: In transition. Cannot toggle.")
        }
    }

    override fun getCurrentElasticState() = state

    private fun setupTransition() {
        if (state == State.EXPANDED) {
            setTransition(expandSceneId, collapseSceneId)
        } else {
            setTransition(collapseSceneId, expandSceneId)
        }
        setTransitionDuration(500)
    }

    companion object {
        private const val TAG = "ElasticView"
    }
}