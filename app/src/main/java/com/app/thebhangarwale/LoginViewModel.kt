package com.app.thebhangarwale

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.*
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Error
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Success
import javax.inject.Inject
import kotlin.Exception

class LoginViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    fun validatedPhoneNumber(phoneNumber : String) : LiveData<BhangarwaleResult<*>> = liveData {
        try {
            if(TextUtils.isEmpty(phoneNumber.trim())){
                throw Exception(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_phone_number)
                )
            }
            if(!TextUtils.isDigitsOnly(phoneNumber.trim())){
                throw Exception(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_phone_number)
                )
            }
            if(!phoneNumber.matches(Regex("\\d{10}"))){
                throw Exception(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_phone_number)
                )
            }
            emit(Success(null))
        }catch (ex : Exception){
            emit(Error(ex))
        }
    }

    fun validatedOTP(otp : String) : LiveData<BhangarwaleResult<*>> = liveData {
        try {
            if(TextUtils.isEmpty(otp.trim())){
                throw Exception(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_otp)
                )
            }
            if(!TextUtils.isDigitsOnly(otp.trim())){
                throw Exception(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_otp)
                )
            }
            if(!otp.matches(Regex("\\d{6}"))){
                throw Exception(
                    getApplication<TheBhangarwaleApplication>()
                        .resources
                        .getString(R.string.error_enter_valid_otp)
                )
            }
            emit(Success(null))
        }catch (ex : Exception){
            emit(Error(ex))
        }
    }

    fun isUserLoggedIn() : LiveData<BhangarwaleResult<*>> = liveData {
        try {
            emit(Success(null))
        }catch (ex : Exception){
            emit(Error(ex))
        }
    }


}