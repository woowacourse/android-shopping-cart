package woowacourse.shopping

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.view.product.ProductActivity

class ProductActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ProductActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @Test
    fun 툴바에_앱이름이_표시된다() {
        onView(withId(R.id.tv_product_toolbar_title)).check(matches(isDisplayed()))
    }

    @Test
    fun 툴바에_장바구니_버튼이_표시된다() {
        onView(withId(R.id.ib_cart)).check(matches(isDisplayed()))
    }

    @Test
    fun `상품_목록이_표시된다`() {
        onView(withId(R.id.rv_products))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `상품_아이템에는_이미지가_표시된다`() {
        onView(withId(R.id.rv_products))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0),
            ).check(matches(hasDescendant(allOf(withId(R.id.iv_product), isDisplayed()))))
    }

    @Test
    fun `상품_아이템에는_이름이_표시된다`() {
        onView(withId(R.id.rv_products))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0),
            ).check(matches(hasDescendant(allOf(withId(R.id.tv_product_name), isDisplayed()))))
    }

    @Test
    fun `상품_아이템에는_가격이_표시된다`() {
        onView(withId(R.id.rv_products))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0),
            ).check(matches(hasDescendant(allOf(withId(R.id.tv_product_price), isDisplayed()))))
    }

    @After
    fun finish() {
        Intents.release()
    }
}
