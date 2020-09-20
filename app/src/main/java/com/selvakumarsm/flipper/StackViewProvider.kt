package com.selvakumarsm.flipper

import java.util.*

class StackViewProvider {

    private val views : Stack<ExpandableView> = Stack()
    private var cursorPosition = -1

    fun loadNext() : ExpandableView? {
        cursorPosition += 1
        if (cursorPosition < getSize())
            views.elementAt(cursorPosition)

        return null
    }

    fun loadPrevious() : ExpandableView? {
        cursorPosition -= 1
        if (cursorPosition >= 0)
            views.elementAt(cursorPosition)
        return null
    }

    fun getSize() : Int = views.size

    fun addView(view: ExpandableView) {
        views.push(view)
    }

    fun addViews(newViews: List<ExpandableView>) {
        views.addAll(newViews)
    }

    fun deleteView(view: ExpandableView) = views.remove(view)

    interface OnStackStateChangeListener {
        fun onViewAdded()
        fun onPreFetchDistanceReached()
    }

    companion object {
        const val TAG = "StackViewProvider"
    }
}