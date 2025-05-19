package woowacourse.shopping.productdetail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.PRODUCT_1
import woowacourse.shopping.R
import woowacourse.shopping.RecyclerViewMatcher.Companion.hasDrawable
import woowacourse.shopping.fakeContext
import woowacourse.shopping.view.productdetail.ProductDetailActivity

class ProductDetailActivityTest {
    private lateinit var intent: Intent

    @Before
    fun setUp() {
        intent =
            Intent(
                fakeContext,
                ProductDetailActivity::class.java,
            ).apply {
                putExtra("product", PRODUCT_1)
            }

        ActivityScenario.launch<ProductDetailActivity>(intent)
    }

    @Test
    fun `선택한_상품의_이름이_보인다`() {
        onView(withText("[병천아우내] 모듬순대"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `선택한_상품의_사진이_보인다`() {
        onView(withId(R.id.iv_product_image))
            .check(matches(hasDrawable()))
    }

    @Test
    fun `선택한_상품의_가격이_보인다`() {
        onView(withText("11,900원"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니_담기_버튼이_보인다`() {
        onView(withId(R.id.tv_add_to_cart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `닫기_버튼이_보인다`() {
        onView(withId(R.id.close_image_btn))
            .check(matches(isDisplayed()))
    }
}
