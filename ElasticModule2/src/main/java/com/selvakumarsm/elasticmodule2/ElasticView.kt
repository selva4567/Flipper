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

    fun expand() {
        Log.d(TAG, "expand: $id")
        transitionToEnd()
        state = State.EXPANDED
    }

    fun collapse() {
        Log.d(TAG, "collapse: $id ")
        transitionToStart()
        state = State.COLLAPSED
    }

    fun toggle() {
        when (state) {
            State.EXPANDED -> collapse()
            State.COLLAPSED -> expand()
            else -> throw IllegalArgumentException("Unsupported view state. Only State.EXPANDED, State.COLLAPSED are supported right now.")
        }
    }
}