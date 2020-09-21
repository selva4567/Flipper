package com.selvakumarsm.flipper.orders.domain.usecase

import com.selvakumarsm.flipper.auth.usecase.GetLoggedInUserIdUseCase
import com.selvakumarsm.flipper.orders.domain.model.Item
import com.selvakumarsm.flipper.orders.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow

class GetItemsInCartUseCase(private val itemsRepository: ItemsRepository, private val getUserIdUseCase: GetLoggedInUserIdUseCase) {

    suspend operator fun invoke(): Flow<List<Item>> {
        val userId = getUserIdUseCase.invoke()
        return itemsRepository.getProductsFromCart(userId)
    }
}