package com.selvakumarsm.flipper.home.data

import android.util.Log
import androidx.paging.PagingSource
import com.selvakumarsm.flipper.home.domain.model.Status

class StatusSource : PagingSource<Int, Status>() {

    //TODO("Handle errors")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Status> {
        val batchNumber = params.key ?: 1
        Log.d(TAG, "load: of size - ${params.loadSize} for batch $batchNumber")
        val statusList : List<Status> = (1..10).map {
            Status().apply {
                message = "My status ${it}"
                userName = "User ${it}"
            }
        }

        //TODO("Implement prevkey")
        return LoadResult.Page(
            data = statusList,
            prevKey = null,
            nextKey = null
        )
    }

    companion object {
        const val TAG = "StatusSource"
    }
}