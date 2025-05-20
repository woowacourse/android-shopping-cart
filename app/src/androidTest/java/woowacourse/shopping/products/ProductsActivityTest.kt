package woowacourse.shopping.products

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.RecyclerViewMatcher
import woowacourse.shopping.fakeContext
import woowacourse.shopping.view.products.ProductsActivity

class ProductsActivityTest {
    private lateinit var intent: Intent
    private lateinit var withRecyclerView: RecyclerViewMatcher

    @Before
    fun setUp() {
        intent =
            Intent(
                fakeContext,
                ProductsActivity::class.java,
            )
        withRecyclerView = RecyclerViewMatcher(R.id.rv_products)

        ActivityScenario.launch<ProductsActivity>(intent)
    }

    @Test
    fun `20번째_상품까지_스크롤하면_더보기_버튼이_보인다`() {
        onView(withId(R.id.rv_products))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))

        Thread.sleep(1000)

        onView((withId(R.id.btn_more)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `스크롤하면_보이지_않았던_다른_상품의_이름과_가격이_보인다`() {
        onView(withId(R.id.rv_products))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8),
            )

        Thread.sleep(1000)

        onView(withText("[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트"))
            .check(matches(isDisplayed()))

        onView(withText("42,000원"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니_버튼이_보인다`() {
        onView(withId(R.id.cart_image_btn))
            .check(matches(isDisplayed()))
    }
}
