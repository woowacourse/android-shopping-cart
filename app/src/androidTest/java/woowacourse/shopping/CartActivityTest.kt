package woowacourse.shopping

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.view.cart.CartActivity

class CartActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(CartActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @Test
    fun 뒤로가기_버튼이_표시된다() {
        onView(withId(R.id.ib_back)).check(matches(isDisplayed()))
    }

    @Test
    fun 툴바에_타이틀이_표시된다() {
        onView(withId(R.id.tv_cart_toolbar_title)).check(matches(isDisplayed()))
    }

    @Test
    fun 장바구니_목록이_표시된다() {
        onView(withId(R.id.rv_cart_product)).check(matches(isDisplayed()))
    }

    @Test
    fun 이전_페이지로_이동하는_버튼이_표시된다() {
        onView(withId(R.id.btn_cart_previous)).check(matches(isDisplayed()))
    }

    @Test
    fun 다음_페이지로_이동하는_버튼이_표시된다() {
        onView(withId(R.id.btn_cart_next)).check(matches(isDisplayed()))
    }

    @Test
    fun 현재_페이지가_표시된다() {
        onView(withId(R.id.tv_cart_page)).check(matches(isDisplayed()))
    }

    @After
    fun finish() {
        Intents.release()
    }
}
