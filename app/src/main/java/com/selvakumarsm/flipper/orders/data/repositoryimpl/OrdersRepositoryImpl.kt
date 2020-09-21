package com.selvakumarsm.flipper.orders.data.repositoryimpl

import com.selvakumarsm.flipper.orders.domain.model.Order
import com.selvakumarsm.flipper.orders.domain.repository.OrdersRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class OrdersRepositoryImpl : OrdersRepository {

    override suspend fun processOrder(order: Order): Flow<Order.Status> = flow {
        delay(3000)
        emit(Order.Status.PAYMENT_COMPLETED)
    }

    override suspend fun getPastOrders(): Flow<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrder(orderId: UUID): Flow<Order> {
        TODO("Not yet implemented")
    }
}