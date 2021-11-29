package com.app.thebhangarwale

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.appbar.MaterialToolbar

class CancelRequestActivity : LocalizationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_cancel)
        findViewById<MaterialToolbar>(R.id.toolbar).setNavigationOnClickListener{
            finish()
        }

        val string_call_us = resources.getString(R.string.string_call_us)
        val spannableStringCallUs = SpannableString(string_call_us)
        findViewById<TextView>(R.id.textview_note).apply {
            spannableStringCallUs.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:0123456789")
                        startActivity(intent)
                    }
                },
                0,
                string_call_us.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            append(" ")
            append(spannableStringCallUs)
            setClickable(true)
            setMovementMethod(LinkMovementMethod.getInstance())
        }


    }

}