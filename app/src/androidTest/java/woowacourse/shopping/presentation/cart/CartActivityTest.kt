package woowacourse.shopping.presentation.cart

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.card.MaterialCardView
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.fixture.getFixtureCartedProducts
import woowacourse.shopping.util.FakeShoppingApplication
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class CartActivityTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val application = context.applicationContext as FakeShoppingApplication

    @Before
    fun setUp() {
        application.cartRepository.removeAll()
    }

    @Test
    fun `장바구니_상품_목록과_페이지_내비게이터가_표출된다`() {
        application.cartRepository.addAll(getFixtureCartedProducts(10))
        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.rv_cart)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_previous_page)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_next_page)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_current_page)).check(matches(withText("1")))
        onView(withId(R.id.rv_cart))
            .check(
                matches(
                    allOf(
                        matchViewHolderAtPosition(
                            0,
                            CartAdapter.CartViewHolder::class.java,
                        ),
                        hasDescendant(withText("사과1")),
                    ),
                )
            )
    }

    @Test
    fun `다음_페이지_이동_버튼을_클릭하면_새로운_장바구니_상품들이_표출된다`() {
        application.cartRepository.addAll(getFixtureCartedProducts(10))
        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.btn_next_page)).perform(click())
        onView(withId(R.id.tv_current_page)).check(matches(withText("2")))
        onView(withId(R.id.rv_cart))
            .check(
                matches(
                    hasDescendant(withText("사과6")),
                )
            )
    }

    @Test
    fun `이전_페이지_이동_버튼을_클릭하면_새로운_장바구니_상품들이_표출된다`() {
        application.cartRepository.addAll(getFixtureCartedProducts(10))
        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.btn_next_page)).perform(click())
        onView(withId(R.id.btn_previous_page)).perform(click())
        onView(withId(R.id.tv_current_page)).check(matches(withText("1")))
        onView(withId(R.id.rv_cart))
            .check(
                matches(
                    hasDescendant(withText("사과1")),
                )
            )
    }

    @Test
    fun `첫_페이지에서_이전_페이지로_넘어갈_수_없다`() {
        application.cartRepository.addAll(getFixtureCartedProducts(5))
        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.btn_previous_page)).perform(click())
        onView(withId(R.id.tv_current_page)).check(matches(withText("1")))
    }

    @Test
    fun `5개_이하의_장바구니_상품이_존재하는_경우_다음_페이지로_넘어갈_수_없다`() {
        application.cartRepository.addAll(getFixtureCartedProducts(5))
        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.btn_next_page)).perform(click())
        onView(withId(R.id.tv_current_page)).check(matches(withText("1")))
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
