package com.app.thebhangarwale

import android.text.InputType
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import com.app.thebhangarwale.login.view.OtpActivity
import com.app.thebhangarwale.login.view.PhoneNumberActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class PhoneNumberActivityTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(PhoneNumberActivity::class.java)

    @Test
    fun testUIText() {
        onView(withId(R.id.textViewVerifyYourNumberLabel)).check(matches(withText("Verify your number")))
        onView(withId(R.id.textViewContactNumberRequireLabel)).check(matches(withText("Your contact number requires for using bhangarwale app")))
        onView(withId(R.id.textInputLayoutCountryCode)).check(matches(hasTextInputLayoutHintText("Country code")))
        onView(withId(R.id.textInputEditTextCountryCode)).check(matches(withText("India (+91)")))
        onView(withId(R.id.textInputEditTextCountryCode)).check(matches(not(isEnabled())))
        onView(withId(R.id.textInputLayoutPhoneNumber)).check(matches(hasTextInputLayoutHintText("Your phone number")))
        onView(withId(R.id.textViewSmsNote)).check(matches(withText("The Bhangarwale will send you a one-off SMS message. Operator rate may apply.")))
        onView(withId(R.id.buttonContinue)).check(matches(withText("Continue")))
    }

    @Test
    fun testValidation(){
        onView(withId(R.id.textInputEditTextPhoneNumber)).perform(typeText(""))
        onView(withId(R.id.buttonContinue)).perform(click())
        onView(withId(R.id.textInputLayoutPhoneNumber)).check(matches(withErrorInInputLayout("Enter valid phone number.")))
    }

    @Test
    fun testPhoneNumberEditextInputTypeShouldBeNumber(){
        onView(withId(R.id.textInputEditTextPhoneNumber)).check(matches(withInputType(InputType.TYPE_CLASS_NUMBER)))
    }

    @Test
    fun testPhoneNumberLengthShouldBe10(){
        onView(withId(R.id.textInputEditTextPhoneNumber)).check(matches(hasMaxLength(10)))
    }

    @Test
    fun testPhoneNumberOnlyAcceptNumber(){
        onView(withId(R.id.textInputEditTextPhoneNumber)).perform(typeText("12345N"))
        getText(withId(R.id.textInputEditTextPhoneNumber))?.matches(Regex("\\D"))
    }

    @Test
    fun testRedirectToOtp(){
        onView(withId(R.id.textInputEditTextPhoneNumber)).perform(typeText("1234567890"))
        onView(withId(R.id.buttonContinue)).perform(click())
        intended(hasComponent(OtpActivity::class.java.name))
    }

    @Test
    fun testRedirectToSupport(){
        onView(withId(R.id.imageviewSupport)).perform(click())
        intended(hasComponent(SupportActivity::class.java.name))
    }

}