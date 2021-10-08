package com.app.thebhangarwale

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.android.material.transition.platform.MaterialFadeThrough

class HomeActivity : BhangarwaleConfigAndControllerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        window.reenterTransition = MaterialFadeThrough().apply {
            excludeTarget(R.id.nav_view,true)
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController : NavController = findNavController(R.id.nav_host_fragment_home)
        navView.setupWithNavController(navController)

        intent?.apply {
            val navigation_id = getIntExtra(REDIRECT_SCREEN,0)
            if(navigation_id!=0){
                navController.navigate(navigation_id)
            }
        }



    }



}