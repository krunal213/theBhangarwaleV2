package com.app.thebhangarwale.home.whatsnew.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import kotlinx.coroutines.delay
import javax.inject.Inject

class WhatIsNewViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    fun getWhatsNew() = liveData<BhangarwaleResult<*>> {
        delay(5000)
        emit(BhangarwaleResult.Success(null))
    }

}