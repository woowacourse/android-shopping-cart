package woowacourse.shopping.ui.detail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.FakeCartRepository
import woowacourse.shopping.data.product.FakeProductRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.data.recent.FakeRecentProductRepository
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.imageUrl
import woowacourse.shopping.price
import woowacourse.shopping.title

@RunWith(AndroidJUnit4::class)
class ProductDetailActivityTest {
    private lateinit var intent: Intent

    @Before
    fun setUp() {
        val fakeProductRepository = FakeProductRepository(listOf(Product(0L, imageUrl, title, price)))
        ProductRepository.setInstance(fakeProductRepository)
        RecentProductRepository.setInstance(FakeRecentProductRepository())
        CartRepository.setInstance(FakeCartRepository())
        intent =
            Intent(ApplicationProvider.getApplicationContext(), ProductDetailActivity::class.java)
                .putExtra("product_id_key", 0L)
    }

    @Test
    fun `상품_제목이_보인다`() {
        ActivityScenario.launch<ProductDetailActivity>(intent)
        onView(withId(R.id.tv_product_detail_title))
            .check(matches(withText(title)))
    }

    @Test
    fun `아직_장바구니에_상품이_담기지_않은_경우_상품_가격_0원이_보인다`() {
        ActivityScenario.launch<ProductDetailActivity>(intent)

        onView(withId(R.id.tv_product_detail_price))
            .check(matches(withText("0원")))
    }

    @Test
    fun `1500원_상품을_3개_담으면_상품_가격_4500원이_보인다`() {
        ActivityScenario.launch<ProductDetailActivity>(intent)

        repeat(3) {
            onView(withId(R.id.btn_cart_quantity_plus))
                .perform(click())
        }

        val expected = "%,d원".format(price * 3)
        onView(withId(R.id.tv_product_detail_price))
            .check(matches(withText(expected)))
    }

    @Test
    fun `상품_개수가_0개인_경우_장바구니_담기_버튼이_비활성화_된다`() {
        ActivityScenario.launch<ProductDetailActivity>(intent)

        onView(withId(R.id.btn_product_detail_add_cart))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun `상품_개수가_1개_이상인_경우_장바구니_담기_버튼이_활성화_된다`() {
        ActivityScenario.launch<ProductDetailActivity>(intent)

        onView(withId(R.id.btn_cart_quantity_plus))
            .perform(click())

        onView(withId(R.id.btn_product_detail_add_cart))
            .check(matches(isEnabled()))
    }
}
