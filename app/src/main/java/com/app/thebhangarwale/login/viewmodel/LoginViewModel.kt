package com.app.thebhangarwale.login.viewmodel

import android.app.Application
import android.content.Context
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.lifecycle.*
import com.app.thebhangarwale.R
import com.app.thebhangarwale.TheBhangarwaleApplication
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Error
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Success
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Loading
import com.app.thebhangarwale.custom.exception.BhangarwaleException
import com.app.thebhangarwale.custom.exception.NoInternetConnectionException
import com.app.thebhangarwale.login.repository.ILoginRepository
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.delay
import java.net.ConnectException
import javax.inject.Inject
import kotlin.Exception

const val INDIA = 91

class LoginViewModel @Inject constructor(
    application: Application,
    val loginRepository: ILoginRepository
) : AndroidViewModel(application) {

    fun validatedPhoneNumber(
        phoneNumber: String,
        fcmToken: String
    ): LiveData<BhangarwaleResult<*>> = liveData {
        try {
            emit(Loading)
            if (TextUtils.isEmpty(phoneNumber.trim())) {
                throw BhangarwaleException(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_phone_number)
                )
            }
            if (!TextUtils.isDigitsOnly(phoneNumber.trim())) {
                throw Exception(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_phone_number)
                )
            }
            if (!phoneNumber.matches(Regex("\\d{10}"))) {
                throw Exception(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_phone_number)
                )
            }
            emit(Success(null))
        } catch (ex: BhangarwaleException) {
            emit(
                Error(
                    ex
                )
            )
        } catch (ex: Exception) {
            emit(
                Error(
                    Exception(
                        getApplication<TheBhangarwaleApplication>()
                            .getString(R.string.error_something_went_wrong)
                    )
                )
            )
        }
    }

    fun validatedOTP(otp: String): LiveData<BhangarwaleResult<*>> = liveData {
        try {
            if (TextUtils.isEmpty(otp.trim())) {
                throw Exception(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_otp)
                )
            }
            if (!TextUtils.isDigitsOnly(otp.trim())) {
                throw Exception(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_otp)
                )
            }
            if (!otp.matches(Regex("\\d{6}"))) {
                throw Exception(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_otp)
                )
            }
            emit(Success(null))
        } catch (ex: Exception) {
            emit(Error(ex))
        }
    }

    fun isUserLoggedIn(): LiveData<BhangarwaleResult<*>> = liveData {
        try {
            emit(Error(Exception()))
        } catch (ex: Exception) {
            emit(Error(ex))
        }
    }

    fun getCurrentCountryCode(): LiveData<BhangarwaleResult<String>> = liveData {
        val telephonyManager =
            getApplication<TheBhangarwaleApplication>().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val countryIso = telephonyManager.simCountryIso.toUpperCase()
        when (PhoneNumberUtil.getInstance().getCountryCodeForRegion(countryIso)) {
            INDIA -> {
                emit(
                    Success(
                        data = countryIso + " (+" + PhoneNumberUtil.getInstance()
                            .getCountryCodeForRegion(countryIso).toString() + ")"
                    )
                )
            }
            else -> {
                emit(
                    Error(
                        Exception(
                            getApplication<TheBhangarwaleApplication>().resources.getString(
                                R.string.string_service_for_nagpur_only
                            )
                        )
                    )
                )
            }
        }
    }


}