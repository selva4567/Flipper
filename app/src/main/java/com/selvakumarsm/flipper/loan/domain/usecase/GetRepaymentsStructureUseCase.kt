package com.selvakumarsm.flipper.loan.domain.usecase

import com.selvakumarsm.flipper.loan.domain.model.RepaymentModel
import com.selvakumarsm.flipper.loan.domain.repository.RepaymentsRepository
import com.selvakumarsm.flipper.loan.domain.repository.RepaymentsResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepaymentsStructureUseCase @Inject constructor(private val repaymentsRepository: RepaymentsRepository) {

    operator fun invoke(): Flow<RepaymentsResult<List<RepaymentModel>>> =
        repaymentsRepository.getRepaymentsStructure()
}