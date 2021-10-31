package com.app.thebhangarwale.login.view

import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.*
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Success
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Error
import com.app.thebhangarwale.dagger.component.DaggerBhangarwaleAppComponent
import com.app.thebhangarwale.dagger.module.BhangarwaleApplicationModule
import com.app.thebhangarwale.databinding.ActivityPhoneNumberBinding
import com.app.thebhangarwale.LoginViewModel
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import javax.inject.Inject

class PhoneNumberActivity : LocalizationActivity(),
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    View.OnClickListener, View.OnFocusChangeListener {

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
                onFocusChangeListener = this@PhoneNumberActivity
                addTextChangedListener {
                    textInputLayoutPhoneNumber
                        .isErrorEnabled = false
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonContinue -> {
                activityPhoneNumberBinding
                    .textInputEditTextPhoneNumber
                    .apply {
                        loginViewModel
                            .validatedPhoneNumber(text.toString().trim())
                            .observe(this@PhoneNumberActivity, {
                                when (it) {
                                    is Success -> {
                                        startActivity(
                                            Intent(
                                                applicationContext,
                                                OtpActivity::class.java
                                            )
                                        )
                                    }
                                    is Error -> {
                                        activityPhoneNumberBinding
                                            .textInputLayoutPhoneNumber
                                            .error = it.exception.message
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

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        try {
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
            val intent =
                Auth.CredentialsApi.getHintPickerIntent(mCredentialsApiClient, hintRequest)
            startIntentSenderForResult(
                intent.intentSender,
                RC_HINT,
                null,
                0,
                0,
                0
            )
        } catch (e: SendIntentException) {
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
