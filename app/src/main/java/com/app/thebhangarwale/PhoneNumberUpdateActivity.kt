package com.app.thebhangarwale

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity

class PhoneNumberUpdateActivity : BhangarwaleConfigAndControllerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number_update)
        findViewById<Button>(R.id.button_continue).setOnClickListener {

            startActivity(Intent(this,OtpUpdateActivity::class.java))
        }
    }

}