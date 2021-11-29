package com.app.thebhangarwale.dagger.module

import android.app.Application
import com.app.thebhangarwale.login.viewmodel.LoginViewModel
import com.app.thebhangarwale.home.feed.repository.FeedRepositoryImpl
import com.app.thebhangarwale.home.feed.viewmodel.FeedViewModel
import com.app.thebhangarwale.home.whatsnew.viewmodel.WhatIsNewViewModel
import com.app.thebhangarwale.login.repository.LoginRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [BhangarwaleApplicationModule::class,BhangarwaleRepositoryModule::class])
class BhangarwaleViewModelModule {

    @Provides
    fun provideLoginViewModel(application: Application,loginRepositoryImpl: LoginRepositoryImpl) = LoginViewModel(application,loginRepositoryImpl)

    @Provides
    fun provideFeedViewModel(application: Application,feedRepositoryImpl: FeedRepositoryImpl) = FeedViewModel(application,feedRepositoryImpl)

    fun provideWhatisNewViewModel(application: Application) = WhatIsNewViewModel(application)

}