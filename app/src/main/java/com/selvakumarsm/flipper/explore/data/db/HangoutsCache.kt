package com.selvakumarsm.flipper.explore.data.db

import com.selvakumarsm.flipper.explore.domain.model.Place

interface HangoutsCache {
    // TODO - Don't persist domain model directly. Create DTO.
    suspend fun getCachedPopularHangouts(): List<Place>
    suspend fun getCachedTrendingHangouts(): List<Place>
}