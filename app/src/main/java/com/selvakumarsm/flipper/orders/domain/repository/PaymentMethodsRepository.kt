package com.selvakumarsm.flipper.orders.domain.repository

import com.selvakumarsm.flipper.orders.domain.model.PaymentMethod
import kotlinx.coroutines.flow.Flow
import java.util.*

interface PaymentMethodsRepository {
    suspend fun getSavedCards(): Flow<List<PaymentMethod.Card>>
    suspend fun saveCard(card: PaymentMethod.Card)
    suspend fun deleteCard(paymentMethodId: UUID)
}