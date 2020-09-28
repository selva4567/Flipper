package com.selvakumarsm.flipper.explore.domain.repository

import com.selvakumarsm.flipper.explore.domain.model.Coordinates
import com.selvakumarsm.flipper.explore.domain.model.Featured
import com.selvakumarsm.flipper.explore.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface HangoutsRepository {
    fun getTrendingNowHangouts(nearBy: Coordinates): Flow<HangoutsResult<List<Place>>>
    fun getPopularAreasNear(coordinates: Coordinates): Flow<HangoutsResult<List<Place>>>
    fun getFeaturedList(coordinates: Coordinates): Flow<HangoutsResult<List<Featured>>>
}

sealed class HangoutsResult<out R> {
    data class Success<T>(val hangouts: T): HangoutsResult<T>()
    data class Failed(val throwable: Throwable): HangoutsResult<Nothing>()
    object InProgress: HangoutsResult<Nothing>()
}