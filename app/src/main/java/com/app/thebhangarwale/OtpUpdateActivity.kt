package com.app.thebhangarwale

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.mukesh.OnOtpCompletionListener
import com.mukesh.OtpView
import java.util.*

class OtpUpdateActivity : BhangarwaleConfigAndControllerActivity(), OnOtpCompletionListener {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        findViewById<OtpView>(R.id.otp_view).setOtpCompletionListener(this)
        findViewById<TextView>(R.id.label_two).append(
            PhoneNumberUtils.formatNumber(
                "+918806616913",
                Locale.getDefault().getCountry()
            )
        )
    }

    override fun onOtpCompleted(otp: String?) {
        val otpIntent: Intent = Intent(this@OtpUpdateActivity, ProfileActivity::class.java)
        this@OtpUpdateActivity.startActivity(otpIntent)
    }

}