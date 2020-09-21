package com.selvakumarsm.flipper.orders.domain.usecase

import com.selvakumarsm.flipper.orders.domain.repository.PaymentMethodsRepository

class GetSavedCardsUseCase(private val paymentMethodsRepository: PaymentMethodsRepository) {
    suspend operator fun invoke() =
        paymentMethodsRepository.getSavedCards()
}
