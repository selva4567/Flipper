package com.selvakumarsm.flipper

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

abstract class ExpandableView @JvmOverloads constructor(
    context: Context,
    initialState: Int = COLLAPSED,
    private val callback: OnStateChangeCallback?
) : FrameLayout(context){

    var currentState: Int
        private set

    init {
        currentState = initialState
        addView(if (currentState == EXPANDED) getExpandedView() else getCollapsedView())
        setOnClickListener {
            Log.d(TAG, "onClicked: ")
            toggleState()
        }
    }

    private fun expand() {
        // Animate expansion
        removeViewAt(0)
        addView(getExpandedView())
        currentState = EXPANDED
        callback?.onViewExpanded()
    }

    private fun collapse() {
        // Animate collapse
        removeViewAt(0)
        addView(getCollapsedView())
        currentState = COLLAPSED
        callback?.onViewCollapsed()
    }

    private fun toggleState() {
        // Make sure animation is completed before changing state
        when (currentState) {
            EXPANDED -> collapse()
            COLLAPSED -> expand()
            else -> Log.d(TAG, "toggleState: cannot toggle state. Another state change in progress.")
        }
    }

    private fun getCollapsedView(): View {
        return LayoutInflater.from(context).inflate(getCollapsedViewResourceId(), this, false)
    }

    private fun getExpandedView(): View {
        return LayoutInflater.from(context).inflate(getExpandedViewResourceId(), this, false)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    abstract fun getCollapsedViewResourceId(): Int

    abstract fun getExpandedViewResourceId(): Int

    interface OnStateChangeCallback {

        fun onViewExpanded()

        fun onViewCollapsed()

    }

    companion object {
        const val TAG = "ExpandableView"

        const val COLLAPSED = 4567
        const val EXPANDED = 4568
        const val IN_TRANSISTION = 4569
    }
}