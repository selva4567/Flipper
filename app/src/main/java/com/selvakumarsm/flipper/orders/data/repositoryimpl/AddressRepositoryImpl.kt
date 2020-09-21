package com.selvakumarsm.flipper.orders.data.repositoryimpl

import com.selvakumarsm.flipper.orders.domain.model.Address
import com.selvakumarsm.flipper.orders.domain.repository.AddressRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

class AddressRepositoryImpl : AddressRepository {
    override suspend fun getAllSavedAddressForUser(userId: UUID): Flow<List<Address>> {
        delay(3000)
        val savedAddress = listOf(Address().apply {
            residentName = "Selvakumar Mohan"
            street = "1-23/330B Sriram Nagar"
            city = "Hyderabad"
            state = "Telangana"
            country = "India"
        })
        return flowOf(savedAddress)
    }

    override suspend fun saveAddress(address: Address) {
        TODO("Not yet implemented")
    }
}