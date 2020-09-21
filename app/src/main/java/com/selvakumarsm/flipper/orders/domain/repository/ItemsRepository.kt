package com.selvakumarsm.flipper.orders.domain.repository

import com.selvakumarsm.flipper.orders.domain.model.Item
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ItemsRepository {
    suspend fun getAllProducts(): Flow<List<Item>>
    suspend fun getProductsFromCart(userId: UUID): Flow<List<Item>>
    suspend fun getMostViewedProducts(): Flow<List<Item>>
}