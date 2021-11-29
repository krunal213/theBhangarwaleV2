package com.app.thebhangarwale

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.Observer
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.custom.view.ProgressBarDialog
import com.app.thebhangarwale.home.create_request.viewmodel.RequestViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

class UpdateItemActivity : LocalizationActivity(), View.OnClickListener {

    private val progressBarDialog by lazy {
        ProgressBarDialog(this).show()
    }

    private val requestViewModel by lazy {
        RequestViewModel(application)
    }

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

        findViewById<MaterialButton>(R.id.button_submit)
            .setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_submit->{
                requestViewModel.updateItem().observe(this, Observer{
                    when(it){
                        is BhangarwaleResult.Loading->{
                            progressBarDialog.show()
                        }
                        is BhangarwaleResult.Success->{
                            progressBarDialog.dismiss()
                            Toast
                                .makeText(this,it.data, Toast.LENGTH_LONG)
                                .apply {
                                    //setGravity(Gravity.CENTER,0,0)
                                    show()
                                }
                        }
                    }
                })
            }
        }

    }


}