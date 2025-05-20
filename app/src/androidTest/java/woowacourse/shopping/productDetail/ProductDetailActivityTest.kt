package woowacourse.shopping.productDetail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.fixture.PRODUCT_LUCKY
import woowacourse.shopping.fixture.fakeContext
import woowacourse.shopping.view.productDetail.ProductDetailActivity

class ProductDetailActivityTest {
    private lateinit var intent: Intent
    private lateinit var scenario: ActivityScenario<ProductDetailActivity>

    @Before
    fun setup() {
        intent =
            Intent(
                fakeContext,
                ProductDetailActivity::class.java,
            ).apply {
                putExtra("woowacourse.shopping.EXTRA_PRODUCT", PRODUCT_LUCKY)
            }
        ActivityScenario.launch<ProductDetailActivity>(intent)

        scenario = ActivityScenario.launch(intent)
    }

    @Test
    fun `인텐트로_넘겨받은_상품의_이름이_보인다`() {
        onView(withId(R.id.productDetailName))
            .check(matches(withText(PRODUCT_LUCKY.name)))
    }

    @Test
    fun `인텐트로_넘겨받은_상품의_가격이_보인다`() {
        onView(withId(R.id.productDetailPrice))
            .check(matches(withText("4,000원")))
    }
}
