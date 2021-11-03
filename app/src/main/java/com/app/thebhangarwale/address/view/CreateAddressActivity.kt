package com.app.thebhangarwale.address.view

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.app.thebhangarwale.R
import com.app.thebhangarwale.address.viewmodel.AddressViewModel
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.custom.view.ProgressBarDialog
import com.app.thebhangarwale.dagger.component.DaggerBhangarwaleAppComponent
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.google.android.material.appbar.MaterialToolbar
import javax.inject.Inject

class CreateAddressActivity : BhangarwaleConfigAndControllerActivity(), View.OnClickListener {

    private val progressBarDialog by lazy {
        ProgressBarDialog(this).show()
    }

    @Inject
    lateinit var addressViewModel : AddressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerBhangarwaleAppComponent
            .builder()
            .bhangarwaleApplicationModule(BhangarwaleApplicationModule(application))
            .build()
            .injectCreateAddressActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_address)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))
        supportActionBar?.title = resources.getString(R.string.title_create_address)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        findViewById<Button>(R.id.button_submit).setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_submit->{
                addressViewModel.submitAddress().observe(this, Observer{
                    when(it){
                        is BhangarwaleResult.Loading->{
                            progressBarDialog?.show()
                        }
                        is BhangarwaleResult.Success->{
                            progressBarDialog?.dismiss()
                            Toast
                                .makeText(this,it.data, Toast.LENGTH_LONG)
                                .show()
                            startActivity(Intent(this,AddressActivity::class.java))
                        }
                    }
                })
            }
        }
    }

}