package com.selvakumarsm.flipper

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selvakumarsm.elasticmodule2.ElasticView
import com.selvakumarsm.elasticmodule2.ElasticStackView
import com.selvakumarsm.elasticmodule2.ElasticViewOrchestrator
import com.selvakumarsm.elasticmodule2.ElasticVerticalView

class MainActivity : AppCompatActivity(), ElasticViewOrchestrator.ViewStateChangeListener {

    private lateinit var frameContainer: ElasticVerticalView
    private lateinit var stackContainer: ElasticStackView
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
    val viewList = mutableListOf<ElasticView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_popular_areas)
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
        frameContainer.layoutConfig = ElasticViewOrchestrator.LayoutConfig(transitionState = ElasticViewOrchestrator.TransitionState.EXPAND_AND_COLLAPSE_CHILD_VIEWS)
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

    fun getView1() : ElasticView {
        val view = layoutInflater.inflate(R.layout.layout_card_template, frameContainer, false) as ElasticView
        view.id = View.generateViewId()
        return view
    }

    fun getView2() : ElasticView {
        val view = layoutInflater.inflate(R.layout.layout_card_template, stackContainer, false) as ElasticView
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