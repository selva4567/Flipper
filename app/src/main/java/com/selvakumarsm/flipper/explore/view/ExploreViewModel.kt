package com.selvakumarsm.flipper.explore.view

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsResult
import com.selvakumarsm.flipper.explore.domain.usecase.GetPopularPlacesUseCase
import com.selvakumarsm.flipper.explore.domain.usecase.GetTrendingPlacesUseCase
import kotlinx.coroutines.flow.collectLatest

class ExploreViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val trendingPlaceUseCase: GetTrendingPlacesUseCase,
    private val popularPlaceUseCase: GetPopularPlacesUseCase
) : ViewModel() {

    companion object {
        private val TAG = ExploreViewModel::class.simpleName
    }

    val popularPlacesLiveData = liveData {
        Log.d(TAG, "Call for popular places: ")
        showProgressBar(true)
        popularPlaceUseCase.invoke().collectLatest {
            when (it) {
                is HangoutsResult.Success -> {
                    Log.d(TAG, "Popular places are retried: ${it.hangouts.size}")
                    showProgressBar(false)
                    emit(it.hangouts)
                }
                else -> Log.d(TAG, "Invalid HangoutResult: $it")
            }
        }
    }

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()
    val progressBarVisibilityLiveData: LiveData<Boolean> = _progressBarVisibilityLiveData

    private fun showProgressBar(visible: Boolean) {
        _progressBarVisibilityLiveData.value = visible
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }
}