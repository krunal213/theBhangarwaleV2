package com.app.thebhangarwale.dagger.module

import com.app.thebhangarwale.home.feed.contract.network.FeedService
import com.app.thebhangarwale.home.feed.repository.FeedRepositoryImpl
import com.app.thebhangarwale.login.contract.network.LoginService
import com.app.thebhangarwale.login.repository.LoginRepositoryImpl
import dagger.Module
import dagger.Provides

@Module(includes = [BhangarwaleNetworkModule::class])
class BhangarwaleRepositoryModule {

    @Provides
    fun provideFeedRepository(feedService: FeedService) = FeedRepositoryImpl(feedService)

    @Provides
    fun provideLoginRepository(loginService: LoginService) = LoginRepositoryImpl(loginService)


}