package com.selvakumarsm.elasticmodule2

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout

class ElasticView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    MotionLayout(context, attrs, defStyleAttr), ElasticProperties {

    var state: State
    private var callback: StateChangeListener? = null
    lateinit var viewTag: String
    private val expandSceneId: Int
    private val collapseSceneId: Int

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.ElasticView, 0, 0)
        collapseSceneId = arr.getResourceId(R.styleable.ElasticView_collapsedSceneId, -1)
        expandSceneId = arr.getResourceId(R.styleable.ElasticView_expandedSceneId, -1)
        state = when (arr.getResourceId(R.styleable.ElasticView_initialState, 0)) {
            0 -> State.COLLAPSED
            1 -> State.EXPANDED
            else -> {
                Log.d(TAG, "Invalid initialstate for view: ")
                State.COLLAPSED
            }
        }

        setupTransition()
        setupCallbacks()
    }

    private fun setupCallbacks() {
        setOnClickListener {
            toggle()
        }
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

    override fun setStateChangeListener(stateChangeListener: StateChangeListener) {
        callback = stateChangeListener
    }

    override fun expand() {
        if (expandSceneId == -1)
            throw IllegalStateException("Invalid expand scene id $expandSceneId")
        Log.d(TAG, "expand: $id")
        callback?.preExpand(this)
        transitionToState(expandSceneId)
        state = State.EXPANDED
    }

    override fun collapse() {
        if (collapseSceneId == -1)
            throw IllegalStateException("Invalid collapse scene id $collapseSceneId")
        Log.d(TAG, "collapse: $id ")
        callback?.preCollapse(this)
        transitionToState(collapseSceneId)
        state = State.COLLAPSED
    }

    override fun toggle() {
        when (state) {
            State.COLLAPSED -> expand()
            State.EXPANDED -> collapse()
            State.IN_TRANSITION -> Log.d(TAG, "toogle: In transition. Cannot toggle.")
        }
    }

    private fun setupTransition() {
        if (state == State.EXPANDED) {
            setTransition(expandSceneId, collapseSceneId)
        } else {
            setTransition(collapseSceneId, expandSceneId)
        }
    }

    companion object {
        private const val TAG = "ElasticView"
    }

    enum class State {
        COLLAPSED, EXPANDED, IN_TRANSITION
    }
}