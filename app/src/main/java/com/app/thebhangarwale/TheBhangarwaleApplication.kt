package com.app.thebhangarwale

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import java.util.Locale

class TheBhangarwaleApplication : LocalizationApplication() {

    override fun getDefaultLanguage(base: Context): Locale {
        return Locale("en")
    }

    override fun onCreate() {
        super.onCreate()
        PreferenceManager
            .getDefaultSharedPreferences(this)
            .apply {
                when (getString("theme", "light")) {
                    "light" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    "dark" -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
            }
    }

}