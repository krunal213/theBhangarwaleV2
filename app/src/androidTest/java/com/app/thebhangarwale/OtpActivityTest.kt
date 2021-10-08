package com.app.thebhangarwale

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.app.thebhangarwale.login.view.OtpActivity
import org.junit.Rule
import org.junit.Test

class OtpActivityTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(OtpActivity::class.java)

    @Test
    fun testUIText() {
        onView(withId(R.id.label_one))
            .check(matches(withText("Verification code")))
        onView(withId(R.id.label_two))
            .check(matches(withText("Please type in the verification code\nsent to +91 88 06 616913")))
        onView(withId(R.id.label_three))
            .check(matches(withText("Didn't receive a code?\nPlease wait")))
    }

    @Test
    fun testFullOtpValidation() {
        onView(withId(R.id.otp_view)).perform(typeText("12"))
        intended(hasComponent(HomeActivity::class.java.name), times(0))
    }

    @Test
    fun testRedirectHomeActivity() {
        onView(withId(R.id.otp_view)).perform(typeText("123456"))
        intended(hasComponent(HomeActivity::class.java.name), times(1))
    }
}