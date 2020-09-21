package com.selvakumarsm.flipper

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.selvakumarsm.elasticmodule2.ElasticVerticalView
import com.selvakumarsm.elasticmodule2.ElasticView
import com.selvakumarsm.elasticmodule2.ElasticViewOrchestrator
import com.selvakumarsm.flipper.orders.presentation.OrdersViewModel
import kotlinx.android.synthetic.main.layout_orders.*

class OrdersActivity : AppCompatActivity(), ElasticViewOrchestrator.ViewStateChangeListener {

    companion object {
        private const val TAG = "OrdersActivity"
        private const val SELECT_PAYMENT_METHOD = "PAYMENT_METHOD"
        private const val SELECT_SHIPPING_ADDRESS = "SHIPPING_ADDRESS"
        private const val REVIEW_ORDER = "REVIEW_ORDER"
    }

    private val ordersViewModel: OrdersViewModel by viewModels()
    private lateinit var frameContainer: ElasticVerticalView
    private lateinit var nextButtom: Button

    private var reviewOrderView: ElasticView? = null
    private var selectShippingDetailsView: ElasticView? = null
    private var selectPaymentMethodView: ElasticView? = null

    var viewIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_orders)

        frameContainer = findViewById(R.id.container)
        frameContainer.layoutConfig =
            ElasticViewOrchestrator.LayoutConfig(transitionState = ElasticViewOrchestrator.TransitionState.EXPAND_AND_COLLAPSE_CHILD_VIEWS)
        frameContainer.viewStateChangeListener = this

        nextButtom = findViewById(R.id.btnNext)
        nextButtom.setOnClickListener {
            val view = when (viewIndex) {
                0 -> {
                    getView(R.layout.layout_order_item).also {
                        it.viewTag = REVIEW_ORDER
                    }
                }
                1 -> {
                    getView(R.layout.layout_shipping_address).also {
                        it.viewTag = SELECT_SHIPPING_ADDRESS
                    }
                }
                2 -> {
                    getView(R.layout.layout_payment_methods).also {
                        it.viewTag = SELECT_PAYMENT_METHOD
                    }
                }
                else -> return@setOnClickListener
            }
            frameContainer.addView(view)
            viewIndex++
        }

        if (savedInstanceState == null) {
            ordersViewModel.savedItemsLiveData.observe(this) {
                Log.d(TAG, "onCreate: Saved Items in cart is received ${it?.size}")
                if (reviewOrderView == null) {
                    reviewOrderView = getView(R.layout.layout_order_item).also {
                        it.viewTag = REVIEW_ORDER
                    }
                    frameContainer.addView(reviewOrderView)

                }
                if (it == null || it.isEmpty())
                    return@observe
                val firstItemInCart = it[0]
                Log.d(TAG, "onCreate: First Item in cart $firstItemInCart")
            }
        }
    }

    fun getView(resource: Int): ElasticView {
        val view = layoutInflater.inflate(
            resource,
            frameContainer,
            false
        ) as ElasticView
        view.id = View.generateViewId()
        return view
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
        when (view.viewTag) {
            REVIEW_ORDER -> {
                btnNext.text = "Select Billing Address"
                btnNext.setOnClickListener {
                    frameContainer.addView(getView(R.layout.layout_shipping_address).also {
                        it.viewTag = SELECT_SHIPPING_ADDRESS
                    })
                }
            }
            SELECT_PAYMENT_METHOD -> {
                btnNext.text = "Proceed To Pay"
            }
            SELECT_SHIPPING_ADDRESS -> {
                btnNext.text = "Select Payment Method"
                btnNext.setOnClickListener {
                    frameContainer.addView(getView(R.layout.layout_payment_methods).also {
                        it.viewTag = SELECT_PAYMENT_METHOD
                    })
                }
            }
            else -> {

            }
        }
    }

    override fun onViewRemoved(view: View) {
        TODO("Not yet implemented")
    }

}