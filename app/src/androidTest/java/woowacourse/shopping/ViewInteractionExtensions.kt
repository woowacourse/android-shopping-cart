package woowacourse.shopping

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.allOf

fun ViewInteraction.checkIsDisplayed(): ViewInteraction = this.check(matches(isDisplayed()))

fun ViewInteraction.performClick(): ViewInteraction = this.perform(click())

fun ViewInteraction.scrollToPosition(position: Int): ViewInteraction =
    this.perform(
        RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position),
    )

fun ViewInteraction.checkHasDescendantWithIdDisplayed(childViewId: Int): ViewInteraction =
    this.check(matches(hasDescendant(allOf(withId(childViewId), isDisplayed()))))
