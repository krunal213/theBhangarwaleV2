package com.app.thebhangarwale.custom.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.HomeActivity
import com.app.thebhangarwale.Login
import com.app.thebhangarwale.LoginViewModel
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.dagger.component.DaggerBhangarwaleAppComponent
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.app.thebhangarwale.login.view.PhoneNumberActivity
import javax.inject.Inject

open class BhangarwaleConfigAndControllerActivity : LocalizationActivity() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerBhangarwaleAppComponent
            .builder()
            .bhangarwaleApplicationModule(BhangarwaleApplicationModule(application))
            .build()
            .injectBhangarwaleConfigAndControllerActivity(this)
        super.onCreate(savedInstanceState)
        loginViewModel
            .isUserLoggedIn()
            .observe(this, Observer {
                when (it) {
                    is BhangarwaleResult.Error -> {
                        val intentLogin : Intent = Intent(this, PhoneNumberActivity::class.java)
                        intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intentLogin)
                        finish()
                    }
                }
            })
    }

}