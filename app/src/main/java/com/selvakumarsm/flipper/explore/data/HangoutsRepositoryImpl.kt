package com.selvakumarsm.flipper.explore.data

import com.selvakumarsm.flipper.explore.data.db.HangoutsCache
import com.selvakumarsm.flipper.explore.domain.model.Coordinates
import com.selvakumarsm.flipper.explore.domain.model.Featured
import com.selvakumarsm.flipper.explore.domain.model.Place
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsRepository
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HangoutsRepositoryImpl @Inject constructor(private val hangoutsCache: HangoutsCache) : HangoutsRepository {

    companion object {
        private val TAG = HangoutsRepositoryImpl::class.simpleName
    }

    override fun getTrendingNowHangouts(nearBy: Coordinates): Flow<HangoutsResult<List<Place>>> = flow {
        emit(HangoutsResult.InProgress)
        val cachedHangouts = hangoutsCache.getCachedTrendingHangouts()
        emit(HangoutsResult.Success(cachedHangouts))
    }

    override fun getPopularAreasNear(coordinates: Coordinates): Flow<HangoutsResult<List<Place>>> = flow {
        emit(HangoutsResult.InProgress)
        val cachedHangouts = hangoutsCache.getCachedPopularHangouts()
        emit(HangoutsResult.Success(cachedHangouts))
    }

    override fun getFeaturedList(coordinates: Coordinates): Flow<HangoutsResult<List<Featured>>> = flow {
        emit(HangoutsResult.InProgress)
        emit(HangoutsResult.Success(hangoutsCache.getCachedFeaturedPlaces()))
    }
}