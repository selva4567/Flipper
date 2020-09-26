package com.selvakumarsm.flipper.explore.data

import com.selvakumarsm.flipper.explore.data.db.HangoutsCache
import com.selvakumarsm.flipper.explore.domain.model.Coordinates
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsRepository
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HangoutsRepositoryImpl @Inject constructor(private val hangoutsCache: HangoutsCache) : HangoutsRepository {

    companion object {
        private val TAG = HangoutsRepositoryImpl::class.simpleName
    }

    override fun getTrendingNowHangouts(nearBy: Coordinates): Flow<HangoutsResult> = flow {
        emit(HangoutsResult.InProgress)
        val cachedHangouts = hangoutsCache.getCachedPopularHangouts()
        emit(HangoutsResult.Success(cachedHangouts))
    }

    override fun getPopularAreasNear(coordinates: Coordinates): Flow<HangoutsResult> = flow {
        emit(HangoutsResult.InProgress)
        val cachedHangouts = hangoutsCache.getCachedPopularHangouts()
        emit(HangoutsResult.Success(cachedHangouts))
    }

    override fun getFeaturedList(coordinates: Coordinates): Flow<HangoutsResult> {
        TODO("Not yet implemented")
    }
}