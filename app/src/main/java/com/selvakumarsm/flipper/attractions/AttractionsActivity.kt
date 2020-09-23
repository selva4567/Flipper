package com.selvakumarsm.flipper.attractions

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.transition.TransitionManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_attractions)

        initViews()

        fab.setOnClickListener {
            addViewToContainer()
        }
    }

    private fun addViewToContainer() {
        view1 = layoutInflater.inflate(R.layout.layout_attraction_item, elasticContainer, false) as ElasticView
        elasticContainer.addView(view1)
        view2 = layoutInflater.inflate(R.layout.layout_attraction_item_2, elasticContainer, false) as ElasticView
        elasticContainer.addView(view2)
    }

    private fun initViews() {
        elasticContainer = findViewById(R.id.elasticContainer)
        elasticContainer.layoutConfig = ElasticViewOrchestrator.LayoutConfig(expandToFullScreen = true, transitionState = ElasticViewOrchestrator.TransitionState.JUST_COLLAPSE)
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