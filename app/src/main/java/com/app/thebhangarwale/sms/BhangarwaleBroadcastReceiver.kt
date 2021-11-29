package com.app.thebhangarwale.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class BhangarwaleBroadcastReceiver : BroadcastReceiver() {

    private var otpReceiveListener: OTPReceiveListener? = null

    override fun onReceive(p0: Context?, intent : Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val extras = intent?.extras
            val status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    // Get SMS message contents
                    var otp: String = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String

                    // Extract one-time code from the message and complete verification
                    // by sending the code back to your server for SMS authenticity.
                    // But here we are just passing it to MainActivity
                    if (otpReceiveListener != null) {
                        otp = otp.replace("<#> Your Bhangarwale code is: ", "").split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].split(" ").get(0)
                        otpReceiveListener?.onOTPReceived(otp)
                    }
                }

                CommonStatusCodes.TIMEOUT ->
                    // Waiting for SMS timed out (5 minutes)
                    // Handle the error ...
                    otpReceiveListener?.onOTPTimeOut()
            }
        }
    }

    fun setOTPReceiveListener(otpReceiveListener : OTPReceiveListener){
        this.otpReceiveListener = otpReceiveListener;
    }

    interface OTPReceiveListener {
        fun onOTPReceived(otp: String)
        fun onOTPTimeOut()
    }
}