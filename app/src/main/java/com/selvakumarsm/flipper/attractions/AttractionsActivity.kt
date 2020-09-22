package com.selvakumarsm.flipper.attractions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selvakumarsm.elasticmodule2.ElasticVerticalView
import com.selvakumarsm.flipper.R

class AttractionsActivity : AppCompatActivity() {

    private lateinit var root: ConstraintLayout
    private lateinit var elasticContainer: ElasticVerticalView
    private lateinit var fab: FloatingActionButton
    private var index = 0
    private lateinit var view1: View
    private lateinit var view2: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_attractions)

        initViews()

        fab?.setOnClickListener {
            val view1 = layoutInflater.inflate(R.layout.layout_attraction_item, elasticContainer, false)
            elasticContainer.addView(view1)
        }
    }

    private fun initViews() {
        root = findViewById(R.id.root)
        fab = findViewById(R.id.fab)
        elasticContainer = findViewById(R.id.elasticContainer)
    }

    private fun addView() {
        if (index == 0) {
            view1 = layoutInflater.inflate(R.layout.layout_attraction_item, root, false)
            view1.id = View.generateViewId()
            root.addView(view1)
            TransitionManager.beginDelayedTransition(root)
            val constraintSet = ConstraintSet()
            constraintSet.clone(root)
            constraintSet.connect(view1.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            constraintSet.connect(view1.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 100)
            constraintSet.connect(view1.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            constraintSet.applyTo(root)
        } else {
            view2 = layoutInflater.inflate(R.layout.layout_attraction_item, root, false)
            view2.id = View.generateViewId()
            root.addView(view2)
            val constraintSet = ConstraintSet()
            constraintSet.clone(root)
            constraintSet.connect(view2.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            constraintSet.connect(view2.id, ConstraintSet.TOP, view1.id, ConstraintSet.BOTTOM)
            constraintSet.connect(view2.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            constraintSet.applyTo(root)
        }
        index++
    }
}