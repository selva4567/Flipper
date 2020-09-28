package com.selvakumarsm.flipper.explore.domain.usecase

import com.selvakumarsm.flipper.explore.domain.model.Featured
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsRepository
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsResult
import com.selvakumarsm.flipper.explore.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFeaturedPlacesUseCase @Inject constructor(
    private val hangoutsRepository: HangoutsRepository,
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(): Flow<HangoutsResult<List<Featured>>> {
        val userLocation = locationRepository.getUserLastKnownLocation()
        return hangoutsRepository.getFeaturedList(userLocation)
    }
}