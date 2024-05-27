package woowacourse.shopping.ui.cart

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.core.AllOf.allOf
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.FakeCartDao
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.Cart
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.ui.cart.adapter.CartViewHolder

@RunWith(AndroidJUnit4::class)
class CartActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(CartActivity::class.java)

    @Test
    fun `화면이_띄워지면_장바구니에_담긴_상품명이_보인다`() {
        onView(withId(R.id.rv_cart))
            .perform(RecyclerViewActions.scrollToPosition<CartViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText("맥북0"), isDisplayed()))))
    }

    @Test
    fun `화면이_띄워지면_장바구니에_담긴_상품의_가격이_보인다`() {
        onView(withId(R.id.rv_cart))
            .perform(RecyclerViewActions.scrollToPosition<CartViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText("100원"), isDisplayed()))))
    }

    @Test
    fun `화면이_띄워지면_이전페이지_이동_버튼이_보인다`() {
        onView(withId(R.id.btn_previous))
            .check(matches(isDisplayed()))
            .check(matches(withText("<")))
    }

    @Test
    fun `화면이_띄워지면_다음페이지_이동_버튼이_보인다`() {
        onView(withId(R.id.btn_next))
            .check(matches(isDisplayed()))
            .check(matches(withText(">")))
    }

    @Test
    fun `화면이_띄워지면_페이지_번호가_보인다`() {
        onView(withId(R.id.tv_page_number))
            .check(matches(isDisplayed()))
            .check(matches(withText("1")))
    }

    @Test
    fun `장바구니의_처음_페이지_번호는_1이다`() {
        onView(withId(R.id.tv_page_number))
            .check(matches(isDisplayed()))
            .check(matches(withText("1")))
    }

    @Test
    fun `다음페이지_버튼을_누르면_2페이지로_간다`() {
        // given

        // when
        onView(withId(R.id.btn_next))
            .perform(click())

        // then
        onView(withId(R.id.tv_page_number))
            .check(matches(isDisplayed()))
            .check(matches(withText("2")))
    }

    @Test
    fun `다음페이지_버튼을_누르면_상품명이_보인다`() {
        // given

        // when
        onView(withId(R.id.btn_next))
            .perform(click())

        // then
        onView(withId(R.id.rv_cart))
            .perform(RecyclerViewActions.scrollToPosition<CartViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText("맥북5"), isDisplayed()))))
    }

    @Test
    fun `다음페이지_버튼을_누르면_상품의_가격이_보인다`() {
        // given

        // when
        onView(withId(R.id.btn_next))
            .perform(click())

        // then
        onView(withId(R.id.rv_cart))
            .perform(RecyclerViewActions.scrollToPosition<CartViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText("100원"), isDisplayed()))))
    }

    @Test
    fun `이전페이지_버튼을_누르면_1페이지로_간다`() {
        // given
        onView(withId(R.id.btn_next))
            .perform(click())

        // when
        onView(withId(R.id.btn_previous))
            .perform(click())

        // then
        onView(withId(R.id.tv_page_number))
            .check(matches(isDisplayed()))
            .check(matches(withText("1")))
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun setUp() {
            repeat(5) {
                CartRepositoryImpl.get(FakeCartDao)
                    .insert(Cart(productId = it.toLong(), quantity = Quantity(1)))
            }
            CartRepositoryImpl.get(FakeCartDao).insert(Cart(productId = 5L, quantity = Quantity(1)))
        }
    }
}
