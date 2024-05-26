package woowacourse.shopping.presentation.cart

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.R

class CartActivityTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(CartActivity::class.java)

//    @Before
//    fun setUp() {
//        DefaultCart.deleteAll()
//    }
//
//    @Test
//    fun `장바구니_상품_목록과_페이지_내비게이터가_표출된다`() {
//        repeat(6) {
//            DefaultCart.addCartItem(it + 1L, 1)
//        }
//        onView(withId(R.id.rv_cart)).check(matches(isDisplayed()))
//        onView(withId(R.id.btn_previous_page)).check(matches(isDisplayed()))
//        onView(withId(R.id.btn_next_page)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_current_page)).check(matches(withText("1")))
//    }
//
//    @Test
//    fun `다음_페이지_이동_버튼을_클릭하면_새로운_장바구니_상품들이_표출된다`() {
//        repeat(6) {
//            DefaultCart.addCartItem(it + 1L, 1)
//        }
//        onView(withId(R.id.btn_next_page)).perform(click())
//        onView(withId(R.id.rv_cart)).check(
//            matches(
//                matchViewHolderAtPosition(
//                    0,
//                    CartAdapter.CartViewHolder::class.java,
//                ),
//            ),
//        )
//    }
//
//    @Test
//    fun `이전_페이지_이동_버튼을_클릭하면_새로운_장바구니_상품들이_표출된다`() {
//        repeat(6) {
//            DefaultCart.addCartItem(it + 1L, 1)
//        }
//        onView(withId(R.id.btn_next_page)).perform(click())
//        onView(withId(R.id.btn_previous_page)).perform(click())
//        onView(withId(R.id.rv_cart)).check(
//            matches(
//                matchViewHolderAtPosition(
//                    0,
//                    CartAdapter.CartViewHolder::class.java,
//                ),
//            ),
//        )
//    }
//
//    private fun matchViewHolderAtPosition(
//        position: Int,
//        viewHolderClass: Class<out RecyclerView.ViewHolder>,
//    ): Matcher<View> {
//        return object : TypeSafeMatcher<View>() {
//            override fun describeTo(description: Description) {
//                description.appendText("Checking ViewHolder at position $position")
//            }
//
//            override fun matchesSafely(view: View): Boolean {
//                if (view !is RecyclerView) return false
//                val viewHolder = view.findViewHolderForAdapterPosition(position)
//                return viewHolder != null &&
//                    viewHolderClass.isInstance(
//                        viewHolder,
//                    )
//            }
//        }
//    }
}
