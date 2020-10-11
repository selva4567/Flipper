package com.selvakumarsm.flipper.loan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selvakumarsm.elasticmodule2.ElasticViewContainer
import com.selvakumarsm.flipper.R
import com.selvakumarsm.flipper.loan.repayment.view.RepaymentsAdapter
import kotlinx.android.synthetic.main.activity_explore.*

class LoanActivity : AppCompatActivity() {
    private lateinit var rootContainer: ElasticViewContainer
    private lateinit var rvRepaymentPlans: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_select_repayment)
        rvRepaymentPlans = findViewById(R.id.rvRepaymentOptions)
        rvRepaymentPlans.also {
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            it.adapter = RepaymentsAdapter()
        }
    }
}