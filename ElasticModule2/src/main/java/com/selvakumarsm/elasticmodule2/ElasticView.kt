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
    MotionLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "CollapsableView"
        const val TRANSITION_DURATION = 2000
    }

    enum class State {
        EXPANDED, COLLAPSED, IN_TRANSITION
    }

    var state: State = State.EXPANDED
    lateinit var viewTag: String
    private val collapsedSceneId: Int
    private val expandedSceneId: Int

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.ElasticView, 0, 0)
        collapsedSceneId = arr.getResourceId(R.styleable.ElasticView_collapsedSceneId, -1)
        expandedSceneId = arr.getResourceId(R.styleable.ElasticView_expandedSceneId, -1)
        arr.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

    }

    internal fun expand() {
        constraintSetIds.forEach {
            Log.d(TAG, "expand: Constraint Id $it")
        }
        Log.d(TAG, "expand: $id to $expandedSceneId")
        setTransition(collapsedSceneId, expandedSceneId)
        setTransitionDuration(TRANSITION_DURATION)
        transitionToEnd()
        state = State.EXPANDED
    }

    internal fun collapse() {
        constraintSetIds.forEach {
            Log.d(TAG, "collapse: Constraint Id $it")
        }
        Log.d(TAG, "collapse: $id to $collapsedSceneId")
        setTransition(expandedSceneId, collapsedSceneId)
        setTransitionDuration(TRANSITION_DURATION)
        transitionToStart()
        state = State.COLLAPSED
    }

    internal fun toggle() {
        when (state) {
            State.EXPANDED -> collapse()
            State.COLLAPSED -> expand()
            else -> throw IllegalArgumentException("Unsupported view state. Only State.EXPANDED, State.COLLAPSED are supported right now.")
        }
    }
}