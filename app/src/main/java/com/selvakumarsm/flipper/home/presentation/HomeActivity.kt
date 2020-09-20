package com.selvakumarsm.flipper.home.presentation

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.selvakumarsm.flipper.R

class HomeActivity : AppCompatActivity() {

    lateinit var mainContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mainContainer = findViewById(R.id.mainContainer)

        
    }
}