package com.selvakumarsm.flipper.orders.domain.model

import java.util.*

class PaymentMethod {
    class Card {
        enum class Issuer {
            VISA, MASTERCARD, AMERICAN_EXPRESS
        }
        var id: UUID? = null

        var cardNum : String? = null

        var cardIssuer : Issuer? = null

        var cardIssuerBank : String? = null

        var cardHolderName : String? = null

        //TODO - override get() and return formatted date
        var expirydate : String? = null

        var cvv : String? = null
    }
}