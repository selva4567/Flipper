package com.selvakumarsm.flipper.explore.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.selvakumarsm.flipper.explore.domain.model.Place
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsResult
import com.selvakumarsm.flipper.explore.domain.usecase.GetPopularPlacesUseCase
import com.selvakumarsm.flipper.explore.domain.usecase.GetTrendingPlacesUseCase
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ExploreViewModel @Inject constructor(
    val trendingPlaceUsecace: GetTrendingPlacesUseCase,
    val popularPlaceUseCase: GetPopularPlacesUseCase
) : ViewModel() {


    companion object {
        private val TAG = ExploreViewModel::class.simpleName
    }

    val trendingPlaces = liveData<List<Place>> {
        popularPlaceUseCase.invoke().collectLatest {
            when (it) {
                is HangoutsResult.InProgress -> {

                }
                is HangoutsResult.Success -> {

                }
                is HangoutsResult.Failed -> {

                }
            }
        }
    }
    val popularPlaces = liveData<List<Place>> { }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }
}