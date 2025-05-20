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
import woowacourse.shopping.checkHasDescendantWithIdDisplayed
import woowacourse.shopping.checkIsDisplayed
import woowacourse.shopping.presentation.product.ProductActivity
import woowacourse.shopping.scrollToPosition

class ProductActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ProductActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @Test
    fun 툴바에_앱_이름이_표시된다() {
        onView(withText(R.string.product_toolbar_text)).checkIsDisplayed()
    }

    @Test
    fun 툴바에_장바구니_버튼이_표시된다() {
        onView(withId(R.id.action_cart)).checkIsDisplayed()
    }

    @Test
    fun `상품_목록이_표시된다`() {
        onView(withId(R.id.rv_products)).checkIsDisplayed()
    }

    @Test
    fun `상품_아이템에는_이미지가_표시된다`() {
        onView(withId(R.id.rv_products))
            .scrollToPosition(0)
            .checkHasDescendantWithIdDisplayed(R.id.iv_product)
    }

    @Test
    fun `상품_아이템에는_이름이_표시된다`() {
        onView(withId(R.id.rv_products))
            .scrollToPosition(0)
            .checkHasDescendantWithIdDisplayed(R.id.tv_product_name)
    }

    @Test
    fun `상품_아이템에는_가격이_표시된다`() {
        onView(withId(R.id.rv_products))
            .scrollToPosition(0)
            .checkHasDescendantWithIdDisplayed(R.id.tv_product_price)
    }

    @After
    fun finish() {
        Intents.release()
    }
}
