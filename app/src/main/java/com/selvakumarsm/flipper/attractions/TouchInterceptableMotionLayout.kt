package com.selvakumarsm.flipper.attractions

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.motion.widget.MotionLayout

class TouchInterceptableMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    MotionLayout(context, attrs, defStyleAttr) {

    companion object {
        val TAG = TouchInterceptableMotionLayout::class.simpleName
    }
    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onInterceptTouchEvent: ${event?.action}")
        return super.onInterceptTouchEvent(event)
    }
}