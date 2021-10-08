package com.app.thebhangarwale

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.view.ViewAnimationUtils
import android.view.WindowManager
import androidx.constraintlayout.motion.widget.MotionLayout
import java.util.*
import android.content.pm.ApplicationInfo
import android.content.Intent
import androidx.fragment.app.Fragment


fun Context.setLang(language: String) {
    resources.apply {
        configuration.apply {
            setLocale(Locale(language))
            updateConfiguration(this, displayMetrics)
        }
    }
}

fun View.circularReval() {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            v: View,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            v.removeOnLayoutChangeListener(this)
            val anim = ViewAnimationUtils.createCircularReveal(
                v,
                v.right,
                v.bottom,
                0f,
                v.height.toFloat()
            )
            anim.duration = 300
            anim.start()
        }
    })
}

fun Context.getHeightForFeed(width: Int, height: Int): Int {
    return (resources.displayMetrics.widthPixels * height) / width
}

fun View.setVisibilityForMotionLayout(visibility: Int) {
    val motionLayout = parent as MotionLayout
    motionLayout.constraintSetIds.forEach {
        val constraintSet = motionLayout.getConstraintSet(it) ?: return@forEach
        constraintSet.setVisibility(this.id, visibility)
        constraintSet.applyTo(motionLayout)
    }
}

fun Activity.openKeyboard() {
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}

fun Activity.hideKeyboard() {
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun Activity.startFacebookActivity(facebookUrl : String){
    var uri: Uri = Uri.parse(facebookUrl)
    try {
        val applicationInfo: ApplicationInfo = packageManager.getApplicationInfo("com.facebook.katana", 0)
        if (applicationInfo.enabled) {
            uri = Uri.parse("fb://facewebmodal/f?href=$facebookUrl")
        }
    } catch (ignored: PackageManager.NameNotFoundException) {
    }
    startActivity(Intent(Intent.ACTION_VIEW, uri))
}

fun Fragment.startFacebookActivity(facebookUrl : String){
    var uri: Uri = Uri.parse(facebookUrl)
    try {
        val applicationInfo: ApplicationInfo? = activity?.packageManager?.getApplicationInfo("com.facebook.katana", 0)
        if (applicationInfo?.enabled == true) {
            uri = Uri.parse("fb://facewebmodal/f?href=$facebookUrl")
        }
    } catch (ignored: PackageManager.NameNotFoundException) {
    }
    startActivity(Intent(Intent.ACTION_VIEW, uri))
}

