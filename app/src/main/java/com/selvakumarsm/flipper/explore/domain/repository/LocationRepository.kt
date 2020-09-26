package com.selvakumarsm.flipper.explore.domain.repository

import com.selvakumarsm.flipper.explore.domain.model.Coordinates

interface LocationRepository {
    suspend fun getUserLastKnownLocation(): Coordinates
}