package com.app.thebhangarwale

import android.text.InputFilter
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import android.widget.TextView
import androidx.test.espresso.Espresso

import androidx.test.espresso.ViewAction

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.CoreMatchers.`is`

fun hasTextInputLayoutHintText(expectedErrorText: String): Matcher<View> {
    return object : TypeSafeMatcher<View?>() {
        public override fun matchesSafely(view: View?): Boolean {
            if (view !is TextInputLayout) {
                return false
            }
            val error = view.hint ?: return false
            val hint = error.toString()
            return expectedErrorText == hint
        }
        override fun describeTo(description: Description) {}
    } as Matcher<View>
}

fun hasMaxLength(actualMaxLength : Int) : Matcher<View> {
    return object : TypeSafeMatcher<View?>() {
        public override fun matchesSafely(view: View?): Boolean {
            if (view !is TextInputEditText) {
                return false
            }
            val expectedMaxLength = view.filters.filterIsInstance<InputFilter.LengthFilter>().firstOrNull()?.max
            return expectedMaxLength == actualMaxLength
        }
        override fun describeTo(description: Description) {}
    } as Matcher<View>
}

fun getText(matcher: Matcher<View?>?): String? {
    val stringHolder = arrayOf<String?>(null)
    onView(matcher).perform(object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "getting text from a TextView"
        }

        override fun perform(uiController: UiController?, view: View) {
            val tv = view as TextView //Save, because of check in getConstraints()
            stringHolder[0] = tv.text.toString()
        }
    })
    return stringHolder[0]
}

fun withErrorInInputLayout(actualText : String): Matcher<View?>? {
    val stringMatcher : Matcher<String> = `is`(actualText)
    checkNotNull(stringMatcher)
    return object : BoundedMatcher<View?, TextInputLayout>(TextInputLayout::class.java) {
        var actualError = ""
        override fun describeTo(description: Description) {
            description.appendText("with error: ")
            stringMatcher.describeTo(description)
            description.appendText("But got: $actualText")
        }
        override fun matchesSafely(textInputLayout: TextInputLayout): Boolean {
            val error = textInputLayout.error
            if (error != null) {
                actualError = error.toString()
                return stringMatcher.matches(actualError)
            }
            return false
        }
    }
}


