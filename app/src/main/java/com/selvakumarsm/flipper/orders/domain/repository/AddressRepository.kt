package com.selvakumarsm.flipper.orders.domain.repository

import com.selvakumarsm.flipper.orders.domain.model.Address
import kotlinx.coroutines.flow.Flow
import java.util.*

interface AddressRepository {
    suspend fun getAllSavedAddressForUser(userId: UUID): Flow<List<Address>>
    suspend fun saveAddress(address: Address)
}