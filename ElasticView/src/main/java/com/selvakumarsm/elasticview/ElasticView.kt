package com.selvakumarsm.elasticview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout

class ElasticView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "ElasticView"
        // TODO - make duration configurable
        const val TRANSITION_DURATION = 1000
    }

    enum class State {
        EXPANDED, COLLAPSED, IN_TRANSITION
    }

    var state: State = State.EXPANDED
        private set
    // ConstraintSet id for the collapsed state. This must be equal to the id specified in motion scene xml file
    private val collapsedSceneId: Int
    // ConstraintSet id for the expanded state. This must be equal to the id specified in motion scene xml file
    private val expandedSceneId: Int

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.ElasticView, 0, 0)
        collapsedSceneId = arr.getInt(R.styleable.ElasticView_collapsedSceneId, -1)
        expandedSceneId = arr.getInt(R.styleable.ElasticView_expandedSceneId, -1)
        arr.recycle()
    }

    internal fun expand() {
        Log.d(TAG, "expand: $id to $expandedSceneId")
        setTransition(collapsedSceneId, expandedSceneId)
        constraintSetIds.forEach {
            Log.d(TAG, "expand: Constraint Id $it")
        }
        setTransitionDuration(TRANSITION_DURATION)
        transitionToEnd()
        state = State.EXPANDED
    }

    internal fun collapse() {
        Log.d(TAG, "collapse: $id to $collapsedSceneId")
        setTransition(expandedSceneId, collapsedSceneId)
        constraintSetIds.forEach {
            Log.d(TAG, "expand: Constraint Id $it")
        }
        setTransitionDuration(TRANSITION_DURATION)
        transitionToEnd()
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