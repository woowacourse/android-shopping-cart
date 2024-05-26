package woowacourse.shopping.presentation.cart

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R

@RunWith(AndroidJUnit4::class)
class CartActivityTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(CartActivity::class.java)

    @Test
    fun `다음_페이지_이동_버튼을_클릭하면_새로운_장바구니_상품들이_표출된다`() {
        onView(withId(R.id.btn_next_page)).perform(click())
        onView(withId(R.id.rv_cart)).check(
            matches(
                matchViewHolderAtPosition(
                    0,
                    CartAdapter.CartViewHolder::class.java,
                ),
            ),
        )
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
