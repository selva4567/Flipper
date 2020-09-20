package com.selvakumarsm.flipper.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.selvakumarsm.flipper.home.data.StatusRepository
import kotlinx.coroutines.flow.collect

class HomeViewModel : ViewModel() {

    private val statusRepository = StatusRepository()

    val recentStatusLiveData = liveData<PagingData<RecentStatus>> {
        Pager(PagingConfig(pageSize = 10, prefetchDistance = 2, initialLoadSize = 10)) {
            statusRepository.getStatusDataSource()
        }.flow.collect {
            val recentStatus : PagingData<RecentStatus> = it.map {status ->
                Log.d(TAG, "Mappping : ${status.message}, ${status.userName}")
                RecentStatus(status.message!!, status.userName!!)
            }
            emit(recentStatus)
        }
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}