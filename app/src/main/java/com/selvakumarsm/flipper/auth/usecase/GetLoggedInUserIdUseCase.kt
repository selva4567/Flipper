package com.selvakumarsm.flipper.auth.usecase

import java.util.*

class GetLoggedInUserIdUseCase {
    //TODO - connect to active session to fetch the user id
    operator fun invoke() = UUID.randomUUID()
}