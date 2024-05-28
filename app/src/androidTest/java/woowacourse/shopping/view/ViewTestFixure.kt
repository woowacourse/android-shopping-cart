package woowacourse.shopping.view

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers

fun onViewClick(id: Int) {
    Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.click())
}
