package com.selvakumarsm.flipper.loan.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selvakumarsm.elasticmodule2.ElasticProperties
import com.selvakumarsm.elasticmodule2.StackViewGroup
import com.selvakumarsm.flipper.R
import com.selvakumarsm.flipper.loan.repayment.view.RepaymentsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoanActivity : AppCompatActivity(), StackViewGroup.ContainerViewStateChangeListener {
    private lateinit var stack: StackViewGroup
    private lateinit var fab: FloatingActionButton
    private lateinit var fabRemove: FloatingActionButton

    // Select Repayment options view
    private lateinit var selectRepaymentView: View
    private lateinit var rvRepaymentPlans: RecyclerView
    private lateinit var toSelectBankButton: Button

    // Loan Amount Selection View
    private lateinit var loanAmountSelectionView: View
    private lateinit var toEmiSelectionButton: Button

    // Select Bank View
    private lateinit var bankSelectionView: View
    private lateinit var toKycButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan)
        stack = findViewById(R.id.rootContainer)
        stack.layoutManager = StackLayoutManager()
        stack.containerViewStateChangeListener = this
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            addLoanAmountSelectionView()
        }
        GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            addLoanAmountSelectionView()
        }
    }

    private fun initBankSelectionView() {
        bankSelectionView =
            layoutInflater.inflate(R.layout.layout_select_account, stack, false)
        toKycButton = bankSelectionView.findViewById(R.id.btnStartKYC)
        bankSelectionView.setOnClickListener {
            Log.d(TAG, "initBankSelectionView: Clicked")
        }
        stack.addView(bankSelectionView)
    }

    private fun initRepaymentView() {
        selectRepaymentView =
            layoutInflater.inflate(R.layout.layout_select_repayment, stack, false)
        rvRepaymentPlans = selectRepaymentView.findViewById(R.id.rvRepaymentOptions)
        rvRepaymentPlans.also {
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            it.adapter = RepaymentsAdapter()
        }
        toSelectBankButton = selectRepaymentView.findViewById(R.id.btnSelectBank)
        toSelectBankButton.setOnClickListener {
            initBankSelectionView()
        }
        selectRepaymentView.setOnClickListener {
            Log.d(TAG, "initRepaymentView: Clicked")
        }
        stack.addView(selectRepaymentView)
    }

    private fun addLoanAmountSelectionView() {
        loanAmountSelectionView =
            layoutInflater.inflate(R.layout.layout_loan_amount_selection, stack, false)
        toEmiSelectionButton = loanAmountSelectionView.findViewById(R.id.btnProceedToEmi)
        toEmiSelectionButton.setOnClickListener {
            initRepaymentView()
        }
        loanAmountSelectionView.setOnClickListener {
            Log.d(TAG, "addLoanAmountSelectionView: Clicked")
            stack.removeViewsOnTopOf(it)
        }
        stack.addView(loanAmountSelectionView)
    }

    override fun onBackPressed() {
        if (stack.canPop())
            stack.pop()
        else
            super.onBackPressed()
    }

    override fun onViewAdded(view: View) {
        Log.d(TAG, "viewAdded: ${view.id}")
        (0 until stack.indexOfChild(view)).forEach {
            Log.d(TAG, "onViewAdded: Collapsing view at $it")
            (stack.getChildAt(it) as ElasticProperties).collapse()
        }
    }

    override fun onViewsRemoved(views: List<View>) {
        Log.d(TAG, "onViewsRemoved: ${views.size}")
    }

    override fun onViewsHidden(views: List<View>) {
        Log.d(TAG, "onViewsHidden: ${views.size}")
    }

    override fun onViewVisible(view: View) {
        Log.d(TAG, "onViewVisible: ${view.id}")
        (view as ElasticProperties).expand()
    }

    companion object {
        val TAG = LoanActivity::class.simpleName
    }
}