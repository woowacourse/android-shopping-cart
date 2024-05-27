package woowacourse.shopping.presentation.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.presentation.home.adapter.LoadingViewHolder
import woowacourse.shopping.presentation.home.adapter.ProductViewHolder

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @get:Rule
    val scenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun `전체_상품_목록을_불러온다`() {
        onView(withId(R.id.rv_product)).check(matches(isDisplayed()))
    }

    @Test
    fun `스크롤을_끝까지_내렸을_때_더보기_버튼이_나타난다`() {
        var itemCount = 0

        scenarioRule.scenario.onActivity { activity ->
            val recyclerView = activity.findViewById<RecyclerView>(R.id.rv_product)
            itemCount = recyclerView.adapter?.itemCount ?: 0
        }

        if (itemCount >= 20) {
            onView(withId(R.id.rv_product)).perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>())
                .check(
                    matches(
                        matchViewHolderAtPosition(
                            20,
                            LoadingViewHolder::class.java,
                        ),
                    ),
                )
        } else {
            onView(withId(R.id.rv_product)).perform(RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>())
            onView(withId(R.id.btn_load_more)).check(matches(not(isDisplayed())))
        }
    }

    private fun matchViewHolderAtPosition(
        position: Int,
        viewHolderClass: Class<out RecyclerView.ViewHolder>,
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Checking ViewHolder at position $position")
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is RecyclerView) return false
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                return viewHolder != null &&
                    viewHolderClass.isInstance(
                        viewHolder,
                    )
            }
        }
    }
}
