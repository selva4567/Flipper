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
        private const val TAG = "ElasticView"
    }

    enum class State {
        EXPANDED, COLLAPSED, IN_TRANSITION
    }

    var state: State = State.EXPANDED
    lateinit var viewTag: String
    var expandSceneId: Int = -1
    var collapseSceneId: Int = -1

    fun expand() {
        if (expandSceneId == -1)
            throw IllegalStateException("Invalid expand scene id $expandSceneId")
        Log.d(TAG, "expand: $id")
        transitionToState(expandSceneId)
        state = State.EXPANDED
    }

    fun collapse() {
        if (collapseSceneId == -1)
            throw IllegalStateException("Invalid collapse scene id $collapseSceneId")
        Log.d(TAG, "collapse: $id ")
        transitionToState(collapseSceneId)
        state = State.COLLAPSED
    }

    fun toggle() {
        when(state) {
            State.COLLAPSED -> expand()
            State.EXPANDED -> collapse()
            State.IN_TRANSITION -> Log.d(TAG, "toogle: In transition. Cannot toggle.")
            else -> Log.d(TAG, "toogle: Cannot toggle. Unknown state.")
        }
    }
}