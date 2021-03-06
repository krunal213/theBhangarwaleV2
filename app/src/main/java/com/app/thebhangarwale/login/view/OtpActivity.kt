package com.app.thebhangarwale.login.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.HomeActivity
import com.app.thebhangarwale.R
import com.app.thebhangarwale.SupportActivity
import com.app.thebhangarwale.custom.adapter.OtpListenerAdapter
import com.app.thebhangarwale.dagger.component.DaggerBhangarwaleAppComponent
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.app.thebhangarwale.databinding.ActivityOtpBinding
import com.app.thebhangarwale.login.viewmodel.LoginViewModel
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.sms.BhangarwaleBroadcastReceiver
import com.google.android.gms.auth.api.phone.SmsRetriever
import javax.inject.Inject

class OtpActivity : LocalizationActivity(), View.OnClickListener,
    BhangarwaleBroadcastReceiver.OTPReceiveListener {

    @Inject
    lateinit var loginViewModel: LoginViewModel
    private val activityOtpBinding: ActivityOtpBinding by lazy {
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
        activityOtpBinding.otpView.otpListener = object : OtpListenerAdapter() {
            override fun onOTPComplete(otp: String) {
                loginViewModel
                    .validatedOTP(otp?.trim())
                    .observe(this@OtpActivity, {
                        when (it) {
                            is BhangarwaleResult.Success -> {
                                startActivity(
                                    Intent(
                                        this@OtpActivity,
                                        HomeActivity::class.java
                                    ).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    })
                            }
                            is BhangarwaleResult.Error -> {
                                activityOtpBinding.otpView.showError()
                            }
                        }
                    })

            }
        }
        /*findViewById<TextView>(R.id.label_two).append(
            PhoneNumberUtils.formatNumber(
                "+918806616913",
                Locale.getDefault().getCountry()
            )
        )*/
        activityOtpBinding.imageviewSupport.setOnClickListener(this)

        SmsRetriever.getClient(this).apply {
            with(startSmsRetriever()) {
                addOnSuccessListener { }
                addOnFailureListener { }
            }
        }

        registerReceiver(BhangarwaleBroadcastReceiver().apply {
            setOTPReceiveListener(this@OtpActivity)
        }, IntentFilter().apply {
            addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
        })


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageviewSupport -> {
                startActivity(Intent(this, SupportActivity::class.java))
            }
        }
    }

    override fun onOTPReceived(otp: String) {
        activityOtpBinding.otpView.setOTP(otp.trim())
    }

    override fun onOTPTimeOut() {
    }


}