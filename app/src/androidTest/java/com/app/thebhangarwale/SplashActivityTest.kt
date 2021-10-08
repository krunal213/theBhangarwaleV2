package com.app.thebhangarwale

import androidx.test.espresso.intent.rule.IntentsTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import android.app.Instrumentation.ActivityMonitor
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.app.thebhangarwale.login.view.PhoneNumberActivity

class SplashActivityTest{

    @get:Rule
    val intentsTestRule = IntentsTestRule(SplashActivity::class.java)

    @Test
    fun testNavigateToPhoneNumberActivity(){
        val activityMonitor: ActivityMonitor = getInstrumentation().addMonitor(PhoneNumberActivity::class.java.getName(), null, false)
        val phoneNumberActivity : PhoneNumberActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000) as PhoneNumberActivity
        assertEquals(PhoneNumberActivity::class.java.simpleName,phoneNumberActivity.javaClass.simpleName)
    }

}