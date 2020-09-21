package com.selvakumarsm.flipper.orders.data.repositoryimpl

import com.selvakumarsm.flipper.orders.domain.model.PaymentMethod
import com.selvakumarsm.flipper.orders.domain.repository.PaymentMethodsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

class PaymentMethodRepositoryImpl : PaymentMethodsRepository {

    override suspend fun getSavedCards(): Flow<List<PaymentMethod.Card>> {
        delay(3000)
        val availablePaymentMethods = listOf(PaymentMethod.Card().apply {
            cardHolderName = "Selvakumar SM"
            cardIssuer = PaymentMethod.Card.Issuer.VISA
            cardNum = "XXXX XXXX XXXX 3029"
            expirydate = "12/32"
        })
        return flowOf(availablePaymentMethods)
    }

    override suspend fun saveCard(card: PaymentMethod.Card) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCard(paymentMethodId: UUID) {
        TODO("Not yet implemented")
    }
}