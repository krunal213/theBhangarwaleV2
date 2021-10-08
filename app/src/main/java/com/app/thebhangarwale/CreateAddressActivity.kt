package com.app.thebhangarwale

import android.os.Bundle
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.appbar.MaterialToolbar

class CreateAddressActivity : BhangarwaleConfigAndControllerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_address)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))
        supportActionBar?.title = resources.getString(R.string.title_create_address)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }

}