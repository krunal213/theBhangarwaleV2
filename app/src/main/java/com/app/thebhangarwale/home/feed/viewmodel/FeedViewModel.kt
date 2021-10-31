package com.app.thebhangarwale.home.feed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class FeedViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    val shareOurAppUrl = "https://play.google.com/store/apps/details?id=com.thekabadiwala.userapp"


}