package com.app.thebhangarwale.dagger.module

import android.app.Application
import com.app.thebhangarwale.login.viewmodel.LoginViewModel
import dagger.Module

@Module(includes = [BhangarwaleApplicationModule::class])
class BhangarwaleViewModelModule {

    fun provideLoginViewModel(application: Application) = LoginViewModel(application)

}