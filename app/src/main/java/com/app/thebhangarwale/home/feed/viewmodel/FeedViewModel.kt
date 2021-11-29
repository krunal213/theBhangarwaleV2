package com.app.thebhangarwale.home.feed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.home.feed.repository.IFeedRepository
import kotlinx.coroutines.delay

class FeedViewModel constructor(application: Application,val feedRepository: IFeedRepository) : AndroidViewModel(application) {

    fun getFeeds() = liveData<BhangarwaleResult<*>> {
        emit(BhangarwaleResult.Loading)
        delay(5000)
        emit(BhangarwaleResult.Success(null))
    }

    val shareOurAppUrl = "https://play.google.com/store/apps/details?id=com.thekabadiwala.userapp"


}