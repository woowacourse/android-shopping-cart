package woowacourse.shopping.ext

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText

fun ViewInteraction.isDisplayed(): ViewInteraction = check(matches(ViewMatchers.isDisplayed()))

fun ViewInteraction.performClick(): ViewInteraction = this.perform(click())

fun ViewInteraction.isTextMatches(other: String): ViewInteraction = check(matches(withText(other)))
