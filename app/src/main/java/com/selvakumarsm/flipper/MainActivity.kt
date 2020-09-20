package com.selvakumarsm.flipper

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selvakumarsm.flipper.flexyview.FlexibleView
import com.selvakumarsm.flipper.flexyview.VerticalStackView

class MainActivity : AppCompatActivity() {

    private lateinit var frameContainer: VerticalStackView
    private lateinit var addButtom: FloatingActionButton
    private lateinit var deleteButton: FloatingActionButton
    private val hashMap = HashMap<Int, String>()
    private val imageResources = listOf(
        R.drawable.fb_1,
        R.drawable.fb_2,
        R.drawable.fb_3,
        R.drawable.fb_4,
        R.drawable.fb_5,
        R.drawable.fb_6,
        R.drawable.fb_7,
        R.drawable.fb_8,
        R.drawable.fb_9,
        R.drawable.fb_10,
        R.drawable.fb_11,
        R.drawable.fb_12
    )
    var imageIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_popular_areas)
        frameContainer = findViewById(R.id.container)
        frameContainer.layoutConfig = FlexibleView.LayoutConfig(viewBehavior = FlexibleView.ViewBehavior.EXPAND_AND_COLLAPSE_CHILD_VIEWS)
        addButtom = findViewById(R.id.add)
        var index = 1
        val view1 = getView1()
        Log.d(TAG, "onCreate: ${view1.id}")
        val view2 = getView1()
        Log.d(TAG, "onCreate: ${view2.id}")
        val view3 = getView1()
        Log.d(TAG, "onCreate: ${view3.id}")
        addButtom.setOnClickListener {
            when (index) {
                1 -> frameContainer.addView(view1)
                2 -> frameContainer.addView(view2)
                3 -> frameContainer.addView(view3)
            }
            index++
        }
        var rIndex = 0
        deleteButton = findViewById(R.id.align)
        deleteButton.setOnClickListener {
            when(rIndex) {
                0 -> frameContainer.removeView(view1)
                1 -> frameContainer.removeView(view2)
                2 -> frameContainer.removeView(view3)
            }
            rIndex++
        }
    }

    fun getView1() : View {
        val view = layoutInflater.inflate(R.layout.layout_card_template, frameContainer, false)
        view.id = View.generateViewId()
        hashMap[view.id] = "collapsed"
        if (view is MotionLayout) {
            view.apply {
                setOnClickListener {
                    if (hashMap[id] == "collapsed") {
                        val motionView = it as MotionLayout
                        motionView.transitionToEnd()
                        hashMap[id] = "expanded"
                    } else {
                        val motionView = it as MotionLayout
                        motionView.transitionToStart()
                        hashMap[id] = "collapsed"
                    }
                }
            }
        }
        return view
    }

    fun getView2() : View {
        val view = layoutInflater.inflate(R.layout.layout_card_template, frameContainer, false)
        view.id = View.generateViewId()
        hashMap[view.id] = "collapsed"
        if (view is MotionLayout) {
            view.findViewById<MaterialCardView>(R.id.cardView).apply {
                setCardBackgroundColor(Color.GREEN)
            }
            view.apply {
                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                setOnClickListener {
                    if (hashMap[id] == "collapsed") {
                        val motionView = it as MotionLayout
                        motionView.transitionToEnd()
                        hashMap[id] = "expanded"
                    } else {
                        val motionView = it as MotionLayout
                        motionView.transitionToStart()
                        hashMap[id] = "collapsed"
                    }
                }
            }
        }
        return view
    }

    companion object {
        const val TAG = "MainActivity"
    }
}