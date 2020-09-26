package com.selvakumarsm.flipper.explore.data.di

import com.selvakumarsm.flipper.explore.data.HangoutsRepositoryImpl
import com.selvakumarsm.flipper.explore.data.LocationRepositoryImpl
import com.selvakumarsm.flipper.explore.data.db.HangoutsCache
import com.selvakumarsm.flipper.explore.data.db.HangoutsCacheImpl
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsRepository
import com.selvakumarsm.flipper.explore.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun provideHangoutsCacheImpl(): HangoutsCache {
        return HangoutsCacheImpl()
    }

    @Provides
    fun provideHangoutRepositoryImpl(hangoutCache: HangoutsCache): HangoutsRepository {
        return HangoutsRepositoryImpl(hangoutCache)
    }

    @Provides
    fun provideLocationRepositoryImpl(): LocationRepository {
        return LocationRepositoryImpl()
    }
}
