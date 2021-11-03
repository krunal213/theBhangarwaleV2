package com.app.thebhangarwale

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aghajari.zoomhelper.ZoomHelper
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.android.material.transition.platform.MaterialFadeThrough

class HomeActivity : LocalizationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        window.reenterTransition = MaterialFadeThrough().apply {
            excludeTarget(R.id.nav_view,true)
            excludeTarget(R.id.layoutProceed,true)
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.apply {
            var badge = getOrCreateBadge(R.id.navigation_what_is_new)
            badge.isVisible = true
            badge.number = 10
        }

        val navController : NavController = findNavController(R.id.nav_host_fragment_home)
        navView.setupWithNavController(navController)

        intent?.apply {
            val navigation_id = getIntExtra(REDIRECT_SCREEN,0)
            if(navigation_id!=0){
                navController.navigate(navigation_id)
            }
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return ev?.let { ZoomHelper.getInstance().dispatchTouchEvent(it,this) } == true || super.dispatchTouchEvent(ev)
    }

}