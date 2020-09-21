package com.selvakumarsm.flipper.orders.domain.model

class Item {
    var productCode: String? = null

    var productName: String? = null

    var productDescription: String? = null

    var price: Double? = null

    var ratingOutOfFive: Float? = null

    //TODO - Replace String with proper Review object
    var userReviews = ArrayList<String>()

    var poductImageUri: String? = null
}