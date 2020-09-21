package com.selvakumarsm.flipper.orders.domain.repository

import com.selvakumarsm.flipper.orders.domain.model.Order
import kotlinx.coroutines.flow.Flow
import java.util.*

interface OrdersRepository {
    suspend fun processOrder(order: Order): Flow<Order.Status>
    suspend fun getPastOrders(): Flow<List<Order>>
    suspend fun getOrder(orderId: UUID): Flow<Order>
}