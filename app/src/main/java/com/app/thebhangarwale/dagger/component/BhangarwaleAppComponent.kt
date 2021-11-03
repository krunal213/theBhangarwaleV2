package com.app.thebhangarwale.dagger.component

import com.app.thebhangarwale.AddressPickerFragment
import com.app.thebhangarwale.ChooseAddressActivity
import com.app.thebhangarwale.EditAddressPickerFragment
import com.app.thebhangarwale.SplashActivity
import com.app.thebhangarwale.address.view.AddressActivity
import com.app.thebhangarwale.address.view.CreateAddressActivity
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.app.thebhangarwale.dagger.module.BhangarwaleViewModelModule
import com.app.thebhangarwale.home.feed.FeedFragment
import com.app.thebhangarwale.home.whatsnew.WhatIsNewFragment
import com.app.thebhangarwale.login.view.OtpActivity
import com.app.thebhangarwale.login.view.PhoneNumberActivity
import dagger.Component

@Component(modules = [BhangarwaleApplicationModule::class,BhangarwaleViewModelModule::class])
interface BhangarwaleAppComponent {

    fun injectPhoneNumberActivity(phoneNumberActivity: PhoneNumberActivity)

    fun injectOtpActivity(otpActivity: OtpActivity)

    fun injectSplashActivity(splashActivity: SplashActivity)

    fun injectBhangarwaleConfigAndControllerActivity(bhangarwaleConfigAndControllerActivity: BhangarwaleConfigAndControllerActivity)

    fun injectFeedFragment(feedFragment: FeedFragment)

    fun injectAddressActivity(addressActivity : AddressActivity)

    fun injectChooseAddressActivity(chooseAddressActivity: ChooseAddressActivity)

    fun injectWhatsNewFragment(whatIsNewFragment: WhatIsNewFragment)

    fun injectCreateAddressActivity(createAddressActivity: CreateAddressActivity)

    fun injectAddressPickerFragment(addressPickerFragment: AddressPickerFragment)

    fun injectEditAddressPickerFragment(editAddressPickerFragment: EditAddressPickerFragment)
}