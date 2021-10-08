package com.app.thebhangarwale

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.TextView
import com.app.thebhangarwale.custom.activity.BhangarwaleConfigAndControllerActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

class SearchRequestInsideCartActivity : BhangarwaleConfigAndControllerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        findViewById<View>(android.R.id.content).transitionName = intent.getStringExtra("transition_name")
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            setAllContainerColors(Color.TRANSPARENT)
            //setFadeMode(MaterialContainerTransform.FADE_MODE_THROUGH)
            setScrimColor(resources.getColor(R.color.color_window_background))
            duration = 3000L

        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            setAllContainerColors(Color.TRANSPARENT)
            //setFadeMode(MaterialContainerTransform.FADE_MODE_THROUGH)
            setScrimColor(resources.getColor(R.color.color_window_background))
            duration = 2500L
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_request_inside_cart)

        setSupportActionBar(findViewById<MaterialToolbar>(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_request_inside_cart, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_voice->{
                displaySpeechRecognizer()
            }
        }
        return true
    }

    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        startActivityForResult(intent, Companion.SPEECH_REQUEST_CODE)
    }

    companion object {
        private const val SPEECH_REQUEST_CODE = 0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { results ->
                    results.get(0)
                }
            findViewById<TextView>(R.id.textView).text = spokenText
            //Toast.makeText(this,spokenText,Toast.LENGTH_LONG).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}