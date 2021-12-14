package ru.donspb.arithmometer20

import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EspressoTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityTopTextView_NotNull() {
        scenario.onActivity {
            val topScreenString = it.findViewById<TextView>(R.id.topScreen)
            TestCase.assertNotNull(topScreenString)
        }
    }

    @Test
    fun activityBotTextView_NotNull() {
        scenario.onActivity {
            val botScreenString = it.findViewById<TextView>(R.id.botScreen)
            TestCase.assertNotNull(botScreenString)
        }
    }

    @Test
    fun activityBotView_HasText() {
        val assertion: ViewAssertion = matches(withText("0"))
        onView(withId(R.id.botScreen)).check(assertion)
    }

    @Test
    fun activityTopTextView_IsDisplayed() {
        onView(withId(R.id.topScreen)).check(matches(isDisplayed()))
    }

    @Test
    fun activityBotTextView_IsDisplayed() {
        onView(withId(R.id.botScreen)).check(matches(isDisplayed()))
    }

    @Test
    fun activityKeyboard_AreEffectiveVisible() {
        onView(withId(R.id.button_0)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_1)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_2)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_3)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_4)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_5)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_6)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_7)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_8)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_9)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_add)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_minus)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_div)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_mult)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_back)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.button_ce)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun activityCeButton_isWorking() {
        twoAddTwo()
        onView(withId(R.id.button_ce)).perform(click())
        onView(withId(R.id.topScreen)).check(matches(withText("")))
        onView(withId(R.id.botScreen)).check(matches(withText("0")))
    }

    @Test
    fun activityNumberButtons_IsWorking() {
        val buttons = mapOf(
            0 to R.id.button_0, 1 to R.id.button_1, 2 to R.id.button_2,
            3 to R.id.button_3, 4 to R.id.button_4, 5 to R.id.button_5,
            6 to R.id.button_6, 7 to R.id.button_7, 8 to R.id.button_8,
            9 to R.id.button_9)
        for ((key, value) in buttons) {
            onView(withId(R.id.button_ce)).perform(click())
            onView(withId(value)).perform(click())
            onView(withId(R.id.botScreen)).check(matches(withText(key.toString())))
        }
    }

    @Test
    fun activityOperationButtons_IsWorking() {
        val buttons = mapOf(
            "+" to R.id.button_add, "-" to R.id.button_minus, "÷" to R.id.button_div,
            "x" to R.id.button_mult)
        for ((key, value) in buttons) {
            onView(withId(value)).perform(click())
            onView(withId(R.id.topScreen)).check(matches(withText("0 $key ")))
        }
    }

    @Test
    fun activityDotButton_IsWorking() {
        onView(withId(R.id.button_dot)).perform(click())
        onView(withId(R.id.botScreen)).check(matches(withText("0.")))
    }

    @Test
    fun activityBackButton_IsWorking() {
        onView(withId(R.id.button_1)).perform(click())
        onView(withId(R.id.button_2)).perform(click())
        onView(withId(R.id.button_3)).perform(click())
        onView(withId(R.id.button_back)).perform(click())
        onView(withId(R.id.botScreen)).check(matches(withText("12")))
    }

    @Test
    fun activityEqualsButton_IsWorking() {
        twoAddTwo()
        onView(withId(R.id.button_equal)).perform(click())
        onView(withId(R.id.botScreen)).check(matches(withText("4.0")))
    }

    @After
    fun close() {
        scenario.close()
    }

    fun twoAddTwo() {
        onView(withId(R.id.button_2)).perform(click())
        onView(withId(R.id.button_add)).perform(click())
        onView(withId(R.id.button_2)).perform(click())
    }
}