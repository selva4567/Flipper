package com.selvakumarsm.flipper.flexyview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import com.selvakumarsm.flipper.R
import java.lang.IllegalArgumentException

class CollapsableView : MotionLayout {

    companion object {
        private const val TAG = "CollapsableView"
        const val TRANSITION_DURATION = 1000
    }

    enum class State {
        EXPANDED, COLLAPSED, IN_TRANSITION
    }

    var state: State = State.EXPANDED
        private set

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    internal fun expand() {
        Log.d(TAG, "expand: $id")
        setTransition(currentState, R.id.expanded)
        setTransitionDuration(TRANSITION_DURATION)
        transitionToEnd()
        state = State.EXPANDED
    }

    internal fun collapse() {
        Log.d(TAG, "collapse: $id")
        setTransition(currentState, R.id.collapsed)
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