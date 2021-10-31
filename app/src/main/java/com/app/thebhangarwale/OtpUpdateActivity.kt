package com.app.thebhangarwale

import `in`.aabhasjindal.otptextview.OtpTextView
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.app.thebhangarwale.custom.adapter.OtpListenerAdapter

import java.util.*

class OtpUpdateActivity : BhangarwaleConfigAndControllerActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        findViewById<OtpTextView>(R.id.otp_view).otpListener = object : OtpListenerAdapter() {
            override fun onOTPComplete(otp: String) {
                val otpIntent: Intent = Intent(this@OtpUpdateActivity, ProfileActivity::class.java)
                this@OtpUpdateActivity.startActivity(otpIntent)
            }
        }
        findViewById<TextView>(R.id.label_two).append(
            PhoneNumberUtils.formatNumber(
                "+918806616913",
                Locale.getDefault().getCountry()
            )
        )
    }

}