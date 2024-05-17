package woowacourse.shopping.ui.products

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.ui.cart.CartViewHolder

@RunWith(AndroidJUnit4::class)
class ProductContentsActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ProductContentsActivity::class.java)

    @Test
    fun `화면이_띄워지면_상품들이_보인다`() {
        onView(withId(R.id.rv_products))
            .perform(RecyclerViewActions.scrollToPosition<ProductViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText("맥북"), isDisplayed()))))
            .check(matches(hasDescendant(allOf(withText("갤럭시북"), isDisplayed()))))
            .check(matches(hasDescendant(allOf(withText("그램"), isDisplayed()))))
            .check(matches(hasDescendant(allOf(withText("아이폰"), isDisplayed()))))
    }

    @Test
    fun `화면이_띄워지면_상품들의_가격이_보인다`() {
        onView(withId(R.id.rv_products))
            .perform(RecyclerViewActions.scrollToPosition<CartViewHolder>(0))
            .check(matches(hasDescendant(AllOf.allOf(withText("100원"), isDisplayed()))))
            .check(matches(hasDescendant(AllOf.allOf(withText("10,000,000원"), isDisplayed()))))
            .check(matches(hasDescendant(AllOf.allOf(withText("10원"), isDisplayed()))))
            .check(matches(hasDescendant(AllOf.allOf(withText("5원"), isDisplayed()))))
    }

    @Test
    fun `화면이_띄워지면_장바구니_이동_버튼이_보인다`() {
        onView(withId(R.id.menu_cart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `스크롤이_가장_아래로_내려가면_더보기_버튼이_보인다`() {
        onView(withId(R.id.rv_products))
            .perform(RecyclerViewActions.scrollToPosition<ProductViewHolder>(19))

        Thread.sleep(1000)

        onView(withId(R.id.btn_load_more))
            .check(matches(isDisplayed()))
            .check(matches(allOf(withText("상품 더보기"), isDisplayed())))
    }
}
