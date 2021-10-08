package com.app.thebhangarwale

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.Window
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

class UpdateItemActivity : BhangarwaleConfigAndControllerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        findViewById<View>(android.R.id.content).transitionName = intent.getStringExtra("transition_name")
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            setAllContainerColors(Color.TRANSPARENT)
            //setFadeMode(MaterialContainerTransform.FADE_MODE_THROUGH)
            setScrimColor(resources.getColor(R.color.color_window_background))
            duration = 300L

        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            setAllContainerColors(Color.TRANSPARENT)
            //setFadeMode(MaterialContainerTransform.FADE_MODE_THROUGH)
            setScrimColor(resources.getColor(R.color.color_window_background))
            duration = 250L
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_item)
        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))
        supportActionBar?.title = resources.getString(R.string.title_update_item)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

}