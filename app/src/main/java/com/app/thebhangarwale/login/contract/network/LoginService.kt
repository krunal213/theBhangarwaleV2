package com.app.thebhangarwale.login.contract.network

import com.app.thebhangarwale.login.entity.PhoneNumberResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

    @POST("sendPhoneNumberAndFcmTokenServerToGetOtp")
    suspend fun sendPhoneNumberAndFcmTokenServerToGetOtp(
        @Query("phoneNumber") phoneNumber: String, @Query("fcmToken") fcmToken: String
    ): PhoneNumberResponse

}