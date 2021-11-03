package com.app.thebhangarwale.home.feed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import kotlinx.coroutines.delay
import javax.inject.Inject

class FeedViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    fun getFeeds() = liveData<BhangarwaleResult<*>> {
        emit(BhangarwaleResult.Loading)
        delay(5000)
        emit(BhangarwaleResult.Success(null))
    }

    val shareOurAppUrl = "https://play.google.com/store/apps/details?id=com.thekabadiwala.userapp"


}