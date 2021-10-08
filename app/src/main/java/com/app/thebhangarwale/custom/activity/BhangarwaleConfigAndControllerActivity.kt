package com.app.thebhangarwale.custom.activity

import android.content.Intent
import android.os.Bundle
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.Login
import com.app.thebhangarwale.login.view.PhoneNumberActivity

open abstract class BhangarwaleConfigAndControllerActivity : LocalizationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!Login.IsLogin){
            val intentLogin : Intent = Intent(this, PhoneNumberActivity::class.java)
            intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intentLogin)
            finish()
        }
    }

}