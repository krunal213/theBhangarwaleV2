package com.app.thebhangarwale.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.app.thebhangarwale.HomeActivity
import com.app.thebhangarwale.Login
import com.app.thebhangarwale.R
import com.app.thebhangarwale.SupportActivity
import com.app.thebhangarwale.dagger.component.DaggerBhangarwaleAppComponent
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.app.thebhangarwale.databinding.ActivityOtpBinding
import com.app.thebhangarwale.login.viewmodel.LoginViewModel
import com.mukesh.OnOtpCompletionListener
import javax.inject.Inject

class OtpActivity : LocalizationActivity(), OnOtpCompletionListener,
    View.OnClickListener {

    @Inject
    lateinit var loginViewModel: LoginViewModel
    private val activityOtpBinding : ActivityOtpBinding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerBhangarwaleAppComponent
            .builder()
            .bhangarwaleApplicationModule(BhangarwaleApplicationModule(application))
            .build()
            .injectOtpActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(activityOtpBinding.root)
        activityOtpBinding.otpView.setOtpCompletionListener(this)
        /*findViewById<TextView>(R.id.label_two).append(
            PhoneNumberUtils.formatNumber(
                "+918806616913",
                Locale.getDefault().getCountry()
            )
        )*/
        activityOtpBinding.imageviewSupport.setOnClickListener(this)
    }

    override fun onOtpCompleted(otp: String?) {
        loginViewModel.validatedOTP(otp?.trim())
        Login.IsLogin = true
        startActivity(Intent(this, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imageviewSupport->{
                startActivity(Intent(this, SupportActivity::class.java))
            }
        }
    }

}