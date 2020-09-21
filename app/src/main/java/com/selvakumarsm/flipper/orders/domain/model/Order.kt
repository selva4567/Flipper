package com.selvakumarsm.flipper.orders.domain.model

import java.util.*
import kotlin.collections.ArrayList

class Order {

    enum class Status {
        DELIVERED, SHIPPED, PAYMENT_FAILED, PAYMENT_PROCESSING, PAYMENT_COMPLETED, PACKAGING
    }

    var invoiceId: UUID? = null
    val items = ArrayList<Item>()
    var totalPrice: Double? = null
    var paymentMethod: PaymentMethod? = null
    var shippingAddress: Address? = null
    var status: Status? = null

}