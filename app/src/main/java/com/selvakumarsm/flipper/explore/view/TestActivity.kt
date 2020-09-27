package com.selvakumarsm.flipper.explore.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selvakumarsm.flipper.R

class TestActivity : AppCompatActivity() {

    private lateinit var elasticContainer: ElasticViewContainer
    private lateinit var fab: FloatingActionButton
    val TAG = TestActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test)
        elasticContainer = findViewById(R.id.elasticContainer)
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.layout_test_item_2, elasticContainer, false)
            elasticContainer.addMotionView(view)
        }
    }
}