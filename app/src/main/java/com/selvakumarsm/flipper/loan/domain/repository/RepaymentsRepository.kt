package com.selvakumarsm.flipper.loan.domain.repository

import com.selvakumarsm.flipper.loan.domain.model.RepaymentModel
import kotlinx.coroutines.flow.Flow

interface RepaymentsRepository {
    fun getRepaymentsStructure(): Flow<RepaymentsResult<List<RepaymentModel>>>
}

sealed class RepaymentsResult<out R> {
    data class Success<T>(val hangouts: T): RepaymentsResult<T>()
    data class Failed(val throwable: Throwable): RepaymentsResult<Nothing>()
    object InProgress: RepaymentsResult<Nothing>()
}