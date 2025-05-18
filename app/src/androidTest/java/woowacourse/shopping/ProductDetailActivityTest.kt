package woowacourse.shopping

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.view.productdetail.ProductDetailActivity

class ProductDetailActivityTest {
    private lateinit var scenario: ActivityScenario<ProductDetailActivity>

    @Before
    fun setUp() {
        val fakeContext = ApplicationProvider.getApplicationContext<Context>()
        val intent =
            Intent(
                fakeContext,
                ProductDetailActivity::class.java,
            ).apply {
                putExtra("product_data", FIXTURE.DUMMY_PRODUCT)
            }
        scenario = ActivityScenario.launch(intent)
    }

    @Test
    fun 툴바에_닫기_버튼이_표시된다() {
        onView(withId(R.id.ib_exit)).check(matches(isDisplayed()))
    }

    @Test
    fun 상품_이미지가_표시된다() {
        onView(withId(R.id.iv_product_detail)).check(matches(isDisplayed()))
    }

    @Test
    fun 상품_이름이_표시된다() {
        onView(withId(R.id.tv_product_detail_name)).check(matches(isDisplayed()))
    }

    @Test
    fun 상품_가격이_표시된다() {
        onView(withId(R.id.tv_product_detail_price)).check(matches(isDisplayed()))
    }

    @Test
    fun 장바구니_담기_버튼이_표시된다() {
        onView(withId(R.id.btn_product_detail_add_cart)).check(matches(isDisplayed()))
    }
}
