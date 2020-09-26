package com.selvakumarsm.flipper.explore.domain.model

data class Place(var name: String) {
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