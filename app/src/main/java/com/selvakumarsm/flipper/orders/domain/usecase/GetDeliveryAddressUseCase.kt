package com.selvakumarsm.flipper.orders.domain.usecase

import com.selvakumarsm.flipper.auth.usecase.GetLoggedInUserIdUseCase
import com.selvakumarsm.flipper.orders.domain.model.Address
import com.selvakumarsm.flipper.orders.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow

class GetDeliveryAddressUseCase(
    private val addressRepository: AddressRepository,
    private val getUserIdUseCase: GetLoggedInUserIdUseCase
) {

    suspend operator fun invoke(): Flow<List<Address>> =
        addressRepository.getAllSavedAddressForUser(getUserIdUseCase.invoke())
}