package com.selvakumarsm.flipper.orders.domain.model

class Address {
    var residentName: String? = null
    var street: String? = null
    var city: String? = null
    var state: String? = null
    var country: String? = null

    //TODO - Move the literals to constant vars
    private val supportedCountries = listOf("india")

    //TODO - Do case insensitive comparison
    fun isServiceAvailableInCountry(): Boolean = supportedCountries.contains(country)
}