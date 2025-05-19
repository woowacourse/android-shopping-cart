package woowacourse.shopping.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.checkIsDisplayed
import woowacourse.shopping.presentation.cart.CartActivity

class CartActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(CartActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @Test
    fun 툴바에_타이틀이_표시된다() {
        onView(withText(R.string.cart_toolbar_text)).checkIsDisplayed()
    }

    @Test
    fun 장바구니_목록이_표시된다() {
        onView(withId(R.id.rv_cart_product)).checkIsDisplayed()
    }

    @Test
    fun 이전_페이지로_이동하는_버튼이_표시된다() {
        onView(withId(R.id.btn_cart_previous)).checkIsDisplayed()
    }

    @Test
    fun 다음_페이지로_이동하는_버튼이_표시된다() {
        onView(withId(R.id.btn_cart_next)).checkIsDisplayed()
    }

    @Test
    fun 현재_페이지가_표시된다() {
        onView(withId(R.id.tv_cart_page)).checkIsDisplayed()
    }

    @After
    fun finish() {
        Intents.release()
    }
}
