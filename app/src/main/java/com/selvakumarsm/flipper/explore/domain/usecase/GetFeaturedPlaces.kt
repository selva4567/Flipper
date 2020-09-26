package com.selvakumarsm.flipper.explore.domain.usecase

import com.selvakumarsm.flipper.explore.domain.model.Coordinates
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsRepository
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFeaturedPlaces @Inject constructor(private val hangoutsRepository: HangoutsRepository) {

    operator fun invoke(): Flow<HangoutsResult> = flow {
        val result = hangoutsRepository.getFeaturedList(Coordinates("", ""))
    }
}