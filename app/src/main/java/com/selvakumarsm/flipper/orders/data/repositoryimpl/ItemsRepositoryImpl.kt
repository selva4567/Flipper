package com.selvakumarsm.flipper.orders.data.repositoryimpl

import com.selvakumarsm.flipper.orders.domain.model.Item
import com.selvakumarsm.flipper.orders.domain.repository.ItemsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

class ItemsRepositoryImpl : ItemsRepository {
    override suspend fun getAllProducts(): Flow<List<Item>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsFromCart(userId: UUID): Flow<List<Item>> {
        delay(3000)
        val itemsInCart = listOf(Item().apply {
            poductImageUri = "http://api.flipper.com/image/1234"
            price = 284.40
            productCode = "Sony WH-1000XM4"
            productDescription =
                "Sony WH-1000XM4 Industry Leading Wireless Noise Cancelling Headphone"
            ratingOutOfFive = 4.5f
        })
        return flowOf(itemsInCart)
    }

    override suspend fun getMostViewedProducts(): Flow<List<Item>> {
        TODO("Not yet implemented")
    }
}