package com.app.thebhangarwale.login.contract.network

import androidx.test.core.app.ApplicationProvider
import com.app.thebhangarwale.buildInternetInterceptor
import com.app.thebhangarwale.custom.exception.NoInternetConnectionException
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import org.junit.Before
import okhttp3.OkHttpClient

class LoginServiceTest {

    var loginService: LoginService? = null

    @Before
    fun setUp() {
        loginService = Retrofit.Builder()
            .baseUrl("http://localhost:8080/login/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient
                    .Builder()
                    .buildInternetInterceptor(ApplicationProvider.getApplicationContext())
            )
            .build()
            .create(LoginService::class.java)
    }

    /**
    *@link https://stackoverflow.com/questions/30474767/
    *no-tests-found-for-given-includes-error-when-running-parameterized-unit-test-in */
    /**
     * Disable Internet Connection First*/
    @Test
    fun test_sendPhoneNumberAndFcmTokenServerToGetOtp_when_no_internet() = runBlocking<Unit> {
        try {
            loginService
                ?.sendPhoneNumberAndFcmTokenServerToGetOtp("8806616913", "127873uuuid8re8r")
        }catch (ex : Exception){
            Assert.assertEquals(NoInternetConnectionException::class.java.name,ex.javaClass.name)
        }
    }

}