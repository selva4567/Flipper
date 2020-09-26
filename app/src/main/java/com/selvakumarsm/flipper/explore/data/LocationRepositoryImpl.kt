package com.selvakumarsm.flipper.explore.data

import com.selvakumarsm.flipper.explore.domain.model.Coordinates
import com.selvakumarsm.flipper.explore.domain.repository.LocationRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor() : LocationRepository {
    override suspend fun getUserLastKnownLocation(): Coordinates {
        delay(1000)
        return Coordinates("1.1", "1.2")
    }
}