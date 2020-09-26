package com.selvakumarsm.flipper.explore.domain.usecase

import com.selvakumarsm.flipper.explore.domain.repository.HangoutsRepository
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsResult
import com.selvakumarsm.flipper.explore.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingPlacesUseCase @Inject constructor(
    private val hangoutsRepository: HangoutsRepository,
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Flow<HangoutsResult> {
        val userLocation = locationRepository.getUserLastKnownLocation()
        return hangoutsRepository.getPopularAreasNear(userLocation)
    }
}