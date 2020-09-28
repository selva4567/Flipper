package com.selvakumarsm.flipper.explore.view

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.selvakumarsm.flipper.explore.domain.repository.HangoutsResult
import com.selvakumarsm.flipper.explore.domain.usecase.GetFeaturedPlacesUseCase
import com.selvakumarsm.flipper.explore.domain.usecase.GetPopularPlacesUseCase
import kotlinx.coroutines.flow.collectLatest

class ExploreViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val featuredPlacesuseCase: GetFeaturedPlacesUseCase,
    private val popularPlaceUseCase: GetPopularPlacesUseCase
) : ViewModel() {

    companion object {
        private val TAG = ExploreViewModel::class.simpleName
    }

    //TODO - use different model for holding view data instead of passing domain model back to the view.
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

    //TODO - use different model for holding view data instead of passing domain model back to the view.
    val featuredPlacesLiveData = liveData {
        showProgressBar(true)
        featuredPlacesuseCase.invoke().collectLatest {
            when (it) {
                is HangoutsResult.Success -> {
                    showProgressBar(false)
                    emit(it.hangouts)
                }
                else -> Log.d(TAG, "Invalid result for featured place request: $it")
            }
        }
    }

    private val _progressBarVisibilityLiveData = MutableLiveData<Boolean>()
    val progressBarVisibilityLiveData: LiveData<Boolean> = _progressBarVisibilityLiveData

    private fun showProgressBar(visible: Boolean) {
        if (!visible)
            if (popularPlacesLiveData.value == null && featuredPlacesLiveData.value == null)
                return
        _progressBarVisibilityLiveData.value = visible
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }
}