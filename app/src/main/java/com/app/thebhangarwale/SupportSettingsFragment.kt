package com.app.thebhangarwale

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.localizationactivity.ui.LocalizationActivity

class SupportSettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_support_settings, rootKey)
        var theme: ListPreference? = findPreference<ListPreference>("theme")
        var language: ListPreference? = findPreference<ListPreference>("language")
        theme?.onPreferenceChangeListener = this
        language?.onPreferenceChangeListener = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                outRect.left = parent.resources.getDimension(R.dimen.margin_eight_dp).toInt()
                outRect.right = parent.resources.getDimension(R.dimen.margin_eight_dp).toInt()
            }
        })
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        when (preference?.key) {
            "theme" -> {
                when (newValue) {
                    "light" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    "dark" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
            }
            "language" -> {
                lateinit var lang : String
                when (newValue) {
                    "hindi"->{
                        lang = "hi"
                    }
                    "english"->{
                        lang = "en"
                    }
                    "marathi"->{
                        lang = "mr"
                    }
                }
                if(lang!=null){
                    with(activity as LocalizationActivity){
                        setLanguage(lang)
                    }
                }
            }
        }
        return true
    }

    override fun setDivider(divider: Drawable?) {
        super.setDivider(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.drawable_horizonatal_divider
            )
        )
    }

}