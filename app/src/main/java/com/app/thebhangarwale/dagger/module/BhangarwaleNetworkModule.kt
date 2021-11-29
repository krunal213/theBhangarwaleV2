package com.app.thebhangarwale.dagger.module

import android.app.Application
import com.app.thebhangarwale.buildInternetInterceptor
import com.app.thebhangarwale.home.feed.contract.network.FeedService
import com.app.thebhangarwale.login.contract.network.LoginService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [BhangarwaleApplicationModule::class])
class BhangarwaleNetworkModule {

    @Provides
    fun provideFeedService(application: Application) = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient
                .Builder()
                .buildInternetInterceptor(application.applicationContext)
        )
        .build()
        .create(FeedService::class.java)

    @Provides
    fun provideLoginService(application: Application) = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient
                .Builder()
                .buildInternetInterceptor(application.applicationContext)
        )
        .build()
        .create(LoginService::class.java)

}