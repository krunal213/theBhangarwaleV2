package com.app.thebhangarwale.dagger.component

import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.app.thebhangarwale.dagger.module.BhangarwaleViewModelModule
import com.app.thebhangarwale.login.view.OtpActivity
import com.app.thebhangarwale.login.view.PhoneNumberActivity
import com.app.thebhangarwale.login.viewmodel.LoginViewModel
import dagger.Component

@Component(modules = [BhangarwaleApplicationModule::class,BhangarwaleViewModelModule::class])
interface BhangarwaleAppComponent {

    fun injectPhoneNumberActivity(phoneNumberActivity: PhoneNumberActivity)

    fun injectOtpActivity(otpActivity: OtpActivity)

}