package com.selvakumarsm.flipper

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selvakumarsm.elasticmodule2.CollapsableView
import com.selvakumarsm.elasticmodule2.FlexibleStackView
import com.selvakumarsm.elasticmodule2.FlexibleView
import com.selvakumarsm.elasticmodule2.VerticalStackView

class OrdersActivity : AppCompatActivity(), FlexibleView.ViewStateChangeListener {

    private lateinit var frameContainer: VerticalStackView
    private lateinit var stackContainer: FlexibleStackView
    private lateinit var addButtom: FloatingActionButton
    private lateinit var deleteButton: FloatingActionButton

    val viewList = mutableListOf<CollapsableView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_orders)
//        stackContainer = findViewById(R.id.container)
//        stackContainer.layoutConfig = FlexibleView.LayoutConfig(transitionState = FlexibleView.TransitionState.JUST_EXPAND)
//        stackContainer.viewStateChangeListener = this
//        addButtom = findViewById(R.id.add)
//        addButtom.setOnClickListener {
//            val view = getView2()
//            viewList.add(view)
//            stackContainer.addView(view)
//        }
//        deleteButton = findViewById(R.id.align)
//        deleteButton.setOnClickListener {
//            val view = viewList.removeAt(0)
//            stackContainer.removeView(view)
//        }

        frameContainer = findViewById(R.id.container)
        frameContainer.layoutConfig =
            FlexibleView.LayoutConfig(transitionState = FlexibleView.TransitionState.EXPAND_AND_COLLAPSE_CHILD_VIEWS)
        frameContainer.viewStateChangeListener = this
        addButtom = findViewById(R.id.add)
        addButtom.setOnClickListener {
            val view = getView1()
            viewList.add(view)
            frameContainer.addView(view)
        }
        deleteButton = findViewById(R.id.align)
        deleteButton.setOnClickListener {
            val view = viewList.removeAt(0)
            frameContainer.removeView(view)
        }
    }

    fun getView1(): CollapsableView {
        val view = layoutInflater.inflate(
            R.layout.layout_order_item,
            frameContainer,
            false
        ) as CollapsableView
        view.id = View.generateViewId()
        return view
    }

    fun getView2(): CollapsableView {
        val view = layoutInflater.inflate(
            R.layout.layout_order_item,
            stackContainer,
            false
        ) as CollapsableView
        view.id = View.generateViewId()
        return view
    }

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onExpanded(view: View) {
        Log.d(TAG, "onExpanded: ${view.id}")
    }

    override fun onCollapsed(view: View) {
        Log.d(TAG, "onCollapsed: ${view.id}")

    }

    override fun onStateTransitionInProgress(view: View) {
        Log.d(TAG, "on animation in progress: ${view.id}")
    }
}