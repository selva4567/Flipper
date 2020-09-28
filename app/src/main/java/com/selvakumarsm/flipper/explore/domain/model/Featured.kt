package com.selvakumarsm.flipper.explore.domain.model

import java.util.*

data class Featured(val id: UUID = UUID.randomUUID()) {
    var resourceId: Int? = null
    var title: String? = null
    var noOfPlaces: Int = 0
    var views: Int = 0
    var followers: Int = 0
    var about: String? = null
    var places: List<Place> = mutableListOf()
}