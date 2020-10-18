package com.selvakumarsm.flipper.loan.data

import com.selvakumarsm.flipper.loan.domain.model.RepaymentModel
import com.selvakumarsm.flipper.loan.domain.repository.RepaymentsRepository
import com.selvakumarsm.flipper.loan.domain.repository.RepaymentsResult
import kotlinx.coroutines.flow.Flow

class RepaymentsRepositoryImpl : RepaymentsRepository {
    override fun getRepaymentsStructure(): Flow<RepaymentsResult<List<RepaymentModel>>> {
        TODO("Not yet implemented")
    }
}