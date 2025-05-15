package woowacourse.shopping.matcher

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText

fun ViewInteraction.performClick(): ViewInteraction = this.perform(click())

fun ViewInteraction.isDisplayed(): ViewInteraction = check(matches(ViewMatchers.isDisplayed()))

fun ViewInteraction.matchText(text: String): ViewInteraction = check(matches(withText(text)))
