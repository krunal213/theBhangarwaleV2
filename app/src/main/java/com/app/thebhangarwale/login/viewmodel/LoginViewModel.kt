package com.app.thebhangarwale.login.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.*
import com.app.thebhangarwale.R
import com.app.thebhangarwale.TheBhangarwaleApplication
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
            emit(Success(Any()))
        }catch (ex : Exception){
            emit(Error(ex))
        }
    }

    fun validatedOTP(trim: String?) {

    }


}