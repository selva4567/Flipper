package com.selvakumarsm.flipper.explore.domain.model

import java.util.*

data class Place(var name: String, val id: UUID = UUID.randomUUID()) {
    var drawableId: Int? = null // Drawable resource id for this place
    var about: String? = null
    var description: String? = null
    var city: String? = null
    var state: String? = null
    var pincode: String? = null
    var distance: String? = null // Distance from user location.
    var timeToReach: String? = null // TIme takes to reach the place from user location
    var coordinates: Coordinates? = null
}

data class Coordinates(val lat: String, val lng: String)