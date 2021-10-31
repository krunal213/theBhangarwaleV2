package com.app.thebhangarwale

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.dagger.component.DaggerBhangarwaleAppComponent
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.app.thebhangarwale.login.view.PhoneNumberActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerBhangarwaleAppComponent
            .builder()
            .bhangarwaleApplicationModule(BhangarwaleApplicationModule(application))
            .build()
            .injectSplashActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loginViewModel
            .isUserLoggedIn()
            .observe(this, Observer {
                when (it) {
                    is BhangarwaleResult.Success -> {
                        Handler().postDelayed({
                            startActivity(Intent(this@SplashActivity, HomeActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            })
                        },5000L)
                    }
                    is BhangarwaleResult.Error -> {
                        Handler().postDelayed({
                            startActivity(Intent(this, PhoneNumberActivity::class.java))
                            finish()
                        }, 5000L)
                    }
                }
            })

    }

}