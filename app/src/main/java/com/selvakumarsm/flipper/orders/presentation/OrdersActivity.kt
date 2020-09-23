package com.selvakumarsm.flipper

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.selvakumarsm.elasticmodule2.ElasticVerticalView
import com.selvakumarsm.elasticmodule2.ElasticView
import com.selvakumarsm.elasticmodule2.ElasticViewOrchestrator
import com.selvakumarsm.flipper.orders.presentation.OrdersViewModel

class OrdersActivity : AppCompatActivity(), ElasticViewOrchestrator.ViewStateChangeListener {

    companion object {
        private const val TAG = "OrdersActivity"
        private const val SELECT_PAYMENT_METHOD = "PAYMENT_METHOD"
        private const val SELECT_SHIPPING_ADDRESS = "SHIPPING_ADDRESS"
        private const val REVIEW_ORDER = "REVIEW_ORDER"
    }

    private val ordersViewModel: OrdersViewModel by viewModels()

    //views
    private lateinit var elasticContainer: ElasticVerticalView
    private lateinit var progressBar: ProgressBar

    private var reviewOrderView: ElasticView? = null
    private var selectShippingDetailsView: ElasticView? = null
    private var selectPaymentMethodView: ElasticView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_orders)
        //view setup
        progressBar = findViewById(R.id.progressBar)
        setupElasticContainer()
        setupOrderReviewPage()
    }

    private fun setupOrderReviewPage() {
        ordersViewModel.savedItemsLiveData.observe(this) {
            if (it == null || it.isEmpty())
                return@observe

            hideProgress()
            // Adding order review screen 1 of 3
            reviewOrderView = getView(R.layout.layout_order_item).apply {
                viewTag = REVIEW_ORDER
            }
            reviewOrderView?.findViewById<TextView>(R.id.tvProductName)?.text = it[0].productCode
            reviewOrderView?.findViewById<TextView>(R.id.tvProductPrice)?.text = "\$${it[0].price}"
            elasticContainer.addView(reviewOrderView)
        }
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun setupElasticContainer() {
        elasticContainer = findViewById(R.id.container)
        elasticContainer.layoutConfig =
            ElasticViewOrchestrator.LayoutConfig(transitionState = ElasticViewOrchestrator.TransitionState.FULL_EXPAND_AND_COLLAPSE_CHILD_VIEWS)
        elasticContainer.viewStateChangeListener = this
    }

    private fun getView(resource: Int): ElasticView {
        return layoutInflater.inflate(
            resource,
            elasticContainer,
            false
        ) as ElasticView
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

    override fun onViewAdded(view: View) {
        if (view !is ElasticView)
            return

    }

    override fun onViewRemoved(view: View) {
        TODO("Not yet implemented")
    }

}