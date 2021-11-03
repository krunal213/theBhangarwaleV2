package com.app.thebhangarwale.dagger.module

import android.app.Application
import com.app.thebhangarwale.LoginViewModel
import com.app.thebhangarwale.home.feed.viewmodel.FeedViewModel
import com.app.thebhangarwale.home.whatsnew.viewmodel.WhatIsNewViewModel
import dagger.Module

@Module(includes = [BhangarwaleApplicationModule::class])
class BhangarwaleViewModelModule {

    fun provideLoginViewModel(application: Application) = LoginViewModel(application)

    fun provideFeedViewModel(application: Application) = FeedViewModel(application)

    fun provideWhatisNewViewModel(application: Application) = WhatIsNewViewModel(application)

}