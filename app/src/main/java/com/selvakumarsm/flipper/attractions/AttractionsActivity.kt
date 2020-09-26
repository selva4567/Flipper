package com.selvakumarsm.flipper.attractions

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selvakumarsm.elasticmodule2.ElasticVerticalView
import com.selvakumarsm.elasticmodule2.ElasticView
import com.selvakumarsm.elasticmodule2.ElasticViewOrchestrator
import com.selvakumarsm.flipper.R

class AttractionsActivity : AppCompatActivity() {

    companion object {
        val TAG = AttractionsActivity::class.simpleName
    }

    private lateinit var elasticContainer: ElasticVerticalView
    private lateinit var fab: FloatingActionButton
    private lateinit var view1: ElasticView
    private lateinit var view2: ElasticView

    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_attractions)

        initViews()

        fab.setOnClickListener {
            addViewToContainer()
        }
    }

    private fun addViewToContainer() {
//        if (index == 0) {
//            view1 = layoutInflater.inflate(
//                R.layout.layout_attraction_item,
//                elasticContainer,
//                false
//            ) as ElasticView
//            elasticContainer.addView(view1.apply {
//                collapseSceneId = R.id.collapsed
//                expandSceneId = R.id.expanded
//            })
//        } else if (index == 1) {
//            view2 = layoutInflater.inflate(
//                R.layout.layout_attraction_item_2,
//                elasticContainer,
//                false
//            ) as ElasticView
//            elasticContainer.addView(view2.apply {
//                collapseSceneId = R.id.collapsed
//                expandSceneId = R.id.expanded
//            })
//        }
//        index++

    }

    private fun initViews() {
        elasticContainer = findViewById(R.id.elasticContainer)
        elasticContainer.layoutConfig = ElasticViewOrchestrator.LayoutConfig(
            transitionState = ElasticViewOrchestrator.TransitionState.FULL_EXPAND_AND_COLLAPSE_CHILD_VIEWS
        )
        fab = findViewById(R.id.fab)
    }

    /**
     * Get px from dp
     */
    private fun fromDp(context: Context, inDp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (inDp * scale).toInt()
    }
}