package com.app.thebhangarwale.login.repository

import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Success
import com.app.thebhangarwale.login.contract.network.LoginService
import com.app.thebhangarwale.login.entity.PhoneNumberResponse
import java.net.ConnectException

class LoginRepositoryImpl(val loginService: LoginService) : ILoginRepository {

    override suspend fun isUserLogin(): Boolean {
        TODO()
    }

    override suspend fun sendPhoneNumberAndFcmTokenServerToGetOtp(
        phoneNumber: String,
        fcmToken: String
    ): BhangarwaleResult<PhoneNumberResponse?> {
        return Success(
            loginService
                .sendPhoneNumberAndFcmTokenServerToGetOtp(phoneNumber, fcmToken)
        )
    }

}