package com.selvakumarsm.elasticmodule2

import android.content.Context
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintLayout

interface LayoutManager {
    fun applyConstraint(child: View, rootView: MotionLayout, containerTransition: MotionScene.Transition)
    fun getLayoutParams(context: Context): ConstraintLayout.LayoutParams
}