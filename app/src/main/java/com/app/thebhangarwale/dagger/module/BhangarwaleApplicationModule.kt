package com.app.thebhangarwale.dagger.module

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class BhangarwaleApplicationModule(val application : Application) {

    @Provides
    fun provideApplication() : Application = application

}