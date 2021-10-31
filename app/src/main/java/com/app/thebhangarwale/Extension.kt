package com.app.thebhangarwale

import android.animation.Animator
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
import kotlin.math.hypot
import kotlin.math.max
import android.animation.AnimatorListenerAdapter
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemService








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
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}

fun Activity.hideKeyboard() {
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun Activity.startFacebookActivity(facebookUrl: String) {
    var uri: Uri = Uri.parse(facebookUrl)
    try {
        val applicationInfo: ApplicationInfo =
            packageManager.getApplicationInfo("com.facebook.katana", 0)
        if (applicationInfo.enabled) {
            uri = Uri.parse("fb://facewebmodal/f?href=$facebookUrl")
        }
    } catch (ignored: PackageManager.NameNotFoundException) {
    }
    startActivity(Intent(Intent.ACTION_VIEW, uri))
}

fun Fragment.startFacebookActivity(facebookUrl: String) {
    var uri: Uri = Uri.parse(facebookUrl)
    try {
        val applicationInfo: ApplicationInfo? =
            activity?.packageManager?.getApplicationInfo("com.facebook.katana", 0)
        if (applicationInfo?.enabled == true) {
            uri = Uri.parse("fb://facewebmodal/f?href=$facebookUrl")
        }
    } catch (ignored: PackageManager.NameNotFoundException) {
    }
    startActivity(Intent(Intent.ACTION_VIEW, uri))
}

fun View.circularRevalV2() {
    if (visibility == View.INVISIBLE) {
        val cx: Int = width / 2
        val cy: Int = height / 2
        val finalRadius: Double = hypot(cx.toDouble(), cy.toDouble())
        val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, finalRadius.toFloat())
        visibility = View.VISIBLE
        anim.start()
    }
}

fun View.hideReval() {
    if (visibility == View.VISIBLE) {
        val cx: Int = getWidth() / 2
        val cy: Int = getHeight() / 2
        val initialRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, initialRadius, 0f)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = View.INVISIBLE
            }
        })
        anim.start()
    }
}

fun EditText.openKeyBoardWithFocus(){
    requestFocus()
    with(context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?){
        this?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}