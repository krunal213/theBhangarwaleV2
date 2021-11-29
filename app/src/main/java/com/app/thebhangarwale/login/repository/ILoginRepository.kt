package com.app.thebhangarwale.login.repository

import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.login.entity.PhoneNumberResponse
import java.net.ConnectException
import kotlin.jvm.Throws


interface ILoginRepository {

    /** Check whether the user is login or not
     * @see LoginRepositoryImpl **/
    suspend fun isUserLogin(): Boolean

    @Throws(ConnectException::class)
    suspend fun sendPhoneNumberAndFcmTokenServerToGetOtp(
        phoneNumber: String,
        fcmToken: String
    ) : BhangarwaleResult<PhoneNumberResponse?>


}