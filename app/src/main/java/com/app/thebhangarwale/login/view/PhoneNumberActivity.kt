package com.app.thebhangarwale.login.view

import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.*
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Loading
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Success
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Error
import com.app.thebhangarwale.custom.view.ProgressBarDialogFragment
import com.app.thebhangarwale.dagger.component.DaggerBhangarwaleAppComponent
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.app.thebhangarwale.databinding.ActivityPhoneNumberBinding
import com.app.thebhangarwale.login.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import androidx.lifecycle.Observer

class PhoneNumberActivity : LocalizationActivity(),
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    View.OnClickListener {

    @Inject
    lateinit var loginViewModel: LoginViewModel
    private val activityPhoneNumberBinding: ActivityPhoneNumberBinding by lazy {
        ActivityPhoneNumberBinding.inflate(layoutInflater)
    }

    companion object {
        private const val RC_HINT = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerBhangarwaleAppComponent
            .builder()
            .bhangarwaleApplicationModule(BhangarwaleApplicationModule(application))
            .build()
            .injectPhoneNumberActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(activityPhoneNumberBinding.root)
        activityPhoneNumberBinding.apply {
            buttonContinue.setOnClickListener(this@PhoneNumberActivity)
            imageviewSupport.setOnClickListener(this@PhoneNumberActivity)
            textInputEditTextPhoneNumber.apply {
                showSoftInputOnFocus = false
                onFocusChangeListener = View.OnFocusChangeListener { p0, p1 ->
                    try {
                        with(this@PhoneNumberActivity) {
                            val mCredentialsApiClient = GoogleApiClient.Builder(this)
                                .addConnectionCallbacks(this)
                                .enableAutoManage(this, this)
                                .addApi(Auth.CREDENTIALS_API)
                                .build()
                            val hintRequest = HintRequest.Builder()
                                .setHintPickerConfig(
                                    CredentialPickerConfig.Builder()
                                        .setShowCancelButton(true)
                                        .build()
                                )
                                .setPhoneNumberIdentifierSupported(true)
                                .build()
                            startIntentSenderForResult(
                                Auth.CredentialsApi
                                    .getHintPickerIntent(
                                        mCredentialsApiClient,
                                        hintRequest
                                    )
                                    .intentSender,
                                RC_HINT,
                                null,
                                0,
                                0,
                                0
                            )
                        }
                    } catch (e: SendIntentException) {
                    }
                }
                addTextChangedListener {
                    textInputLayoutPhoneNumber
                        .isErrorEnabled = false
                }
            }
        }
        loginViewModel.getCurrentCountryCode().observe(this, Observer {
            when (it) {
                is Success -> {
                    activityPhoneNumberBinding
                        .textInputEditTextCountryCode
                        .setText(it.data)
                }
                is Error -> {
                    it.exception.message?.let { message ->
                        Snackbar.make(
                            activityPhoneNumberBinding.root,
                            message,
                            Snackbar.LENGTH_LONG
                        ).setAnchorView(activityPhoneNumberBinding.buttonContinue).show()
                    }
                }
            }
        })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonContinue -> {
                activityPhoneNumberBinding
                    .textInputEditTextPhoneNumber
                    .apply {
                        loginViewModel
                            .validatedPhoneNumber(text.toString().trim(), "")
                            .observe(this@PhoneNumberActivity, {
                                when (it) {
                                    is Loading -> {
                                        with(this@PhoneNumberActivity) {
                                            ProgressBarDialogFragment()
                                                .show(
                                                    supportFragmentManager,
                                                    ProgressBarDialogFragment.TAG
                                                )
                                        }
                                    }
                                    is Success -> {
                                        startActivity(
                                            Intent(
                                                applicationContext,
                                                OtpActivity::class.java
                                            )
                                        )
                                        with(this@PhoneNumberActivity) {
                                            supportFragmentManager.executePendingTransactions()
                                            with(
                                                supportFragmentManager
                                                    .findFragmentByTag(ProgressBarDialogFragment.TAG)
                                                        as ProgressBarDialogFragment
                                            ) {
                                                dismiss()
                                            }
                                        }
                                    }
                                    is Error -> {
                                        with(this@PhoneNumberActivity) {
                                            supportFragmentManager.executePendingTransactions()
                                            with(
                                                supportFragmentManager
                                                    .findFragmentByTag(ProgressBarDialogFragment.TAG)
                                                        as ProgressBarDialogFragment
                                            ) {
                                                dismiss()
                                            }
                                        }
                                        when (it.exception) {
                                            else -> {
                                                activityPhoneNumberBinding
                                                    .textInputLayoutPhoneNumber
                                                    .error = it.exception.message
                                            }
                                        }

                                    }
                                }
                            })
                    }
            }
            R.id.imageviewSupport -> {
                startActivity(Intent(this, SupportActivity::class.java))
            }
        }
    }

    override fun onConnected(p0: Bundle?) {}
    override fun onConnectionSuspended(p0: Int) {}
    override fun onConnectionFailed(p0: ConnectionResult) {}
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    RC_HINT -> {
                        val credential: Credential? = data?.getParcelableExtra(Credential.EXTRA_KEY)
                        if (credential != null) {
                            activityPhoneNumberBinding
                                .textInputEditTextPhoneNumber
                                .setText(
                                    credential.id.substring(3, 13)
                                )
                        }
                        activityPhoneNumberBinding
                            .textInputEditTextPhoneNumber
                            .apply {
                                showSoftInputOnFocus = true
                                setSelection(length())
                            }
                    }
                }
            }
            Activity.RESULT_CANCELED -> {
                when (requestCode) {
                    RC_HINT -> {
                        openKeyboard()
                        activityPhoneNumberBinding
                            .textInputEditTextPhoneNumber
                            .apply {
                                showSoftInputOnFocus = true
                                setSelection(length())
                            }
                    }
                }
            }
        }
    }


}
