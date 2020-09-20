package com.selvakumarsm.flipper.flexyview

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout
import java.lang.IllegalArgumentException

class CollapsableView : MotionLayout {

    companion object {
        private const val TAG = "CollapsableView"
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
        transitionToEnd()
        state = State.EXPANDED
    }

    internal fun collapse() {
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