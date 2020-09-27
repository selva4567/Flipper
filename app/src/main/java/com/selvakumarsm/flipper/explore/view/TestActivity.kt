package com.selvakumarsm.flipper.explore.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selvakumarsm.flipper.R

class TestActivity : AppCompatActivity() {

    private lateinit var elasticContainer: ElasticViewContainer
    private lateinit var fab: FloatingActionButton
    val TAG = TestActivity::class.simpleName
    val images = arrayOf(R.drawable.food_1, R.drawable.food_2)
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test)
        elasticContainer = findViewById(R.id.elasticContainer)
        elasticContainer.layoutManager = GridLayoutManager()
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.layout_featured_item, elasticContainer, false)
            view.findViewById<ImageView>(R.id.featuredImage).setImageResource(images[index++])
            elasticContainer.addView(view)
        }
    }
}