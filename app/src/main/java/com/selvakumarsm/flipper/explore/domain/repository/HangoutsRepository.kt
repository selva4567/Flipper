package com.selvakumarsm.flipper.explore.domain.repository

import com.selvakumarsm.flipper.explore.domain.model.Coordinates
import com.selvakumarsm.flipper.explore.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface HangoutsRepository {
    fun getTrendingNowHangouts(nearBy: Coordinates): Flow<HangoutsResult>
    fun getPopularAreasNear(coordinates: Coordinates): Flow<HangoutsResult>
    fun getFeaturedList(coordinates: Coordinates): Flow<HangoutsResult>
}

sealed class HangoutsResult {
    data class Success(val hangouts: List<Place>): HangoutsResult()
    data class Failed(val throwable: Throwable): HangoutsResult()
    object InProgress: HangoutsResult()
}