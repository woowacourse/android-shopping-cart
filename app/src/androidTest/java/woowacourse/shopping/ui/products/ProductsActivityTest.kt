package woowacourse.shopping.ui.products

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToLastPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.recent.FakeRecentProductRepository
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.firstProduct
import woowacourse.shopping.ui.products.adapter.ProductsViewHolder

@RunWith(AndroidJUnit4::class)
class ProductsActivityTest {
    @Before
    fun setUp() {
        RecentProductRepository.setInstance(FakeRecentProductRepository())
    }

    @Test
    fun `상품_목록이_보인다`() {
        ActivityScenario.launch(ProductsActivity::class.java)
        onView(withId(R.id.rv_products)).check(matches(isDisplayed()))
    }

    @Test
    fun `상품의_제목이_보인다`() {
        ActivityScenario.launch(ProductsActivity::class.java)

        onView(withId(R.id.rv_products))
            .perform(scrollToPosition<ProductsViewHolder.ProductViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText(firstProduct.title), isDisplayed()))))
    }

    @Test
    fun `상품의_가격이_보인다`() {
        ActivityScenario.launch(ProductsActivity::class.java)

        val expected = "%,d원".format(firstProduct.price)
        onView(withId(R.id.rv_products))
            .perform(scrollToPosition<ProductsViewHolder.ProductViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText(expected), isDisplayed()))))
    }

    @Test
    fun `상품_목록이_최상단일_때_더보기_버튼이_보이지_않는다`() {
        ActivityScenario.launch(ProductsActivity::class.java)

        onView(withId(R.id.rv_products))
            .perform(scrollToPosition<ProductsViewHolder.ProductViewHolder>(0))
        onView(withId(R.id.btn_products_load_more))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun `20번째_상품이_화면_최하단에_닿기_전까지_더보기_버튼이_보이지_않는다`() {
        ActivityScenario.launch(ProductsActivity::class.java)

        onView(withId(R.id.rv_products))
            .perform(scrollToPosition<ProductsViewHolder.ProductViewHolder>(10))
        onView(withId(R.id.btn_products_load_more))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun `20번째_상품이_최하단에_닿으면_더보기_버튼이_보인다`() {
        ActivityScenario.launch(ProductsActivity::class.java)

        onView(withId(R.id.rv_products))
            .perform(scrollToLastPosition<ProductsViewHolder.ProductViewHolder>())
        onView(withId(R.id.btn_products_load_more))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }
}
