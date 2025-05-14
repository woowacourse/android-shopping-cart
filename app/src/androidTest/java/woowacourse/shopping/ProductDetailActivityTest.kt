@file:Suppress("ktlint")

package woowacourse.shopping

import android.annotation.SuppressLint
import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.detail.ProductDetailActivity

class ProductDetailActivityTest {
    private lateinit var scenario: ActivityScenario<ProductDetailActivity>

    @get:Rule
    val productDetailActivityScenarioRule = ActivityScenarioRule(ProductDetailActivity::class.java)

    @Before
    fun setUp() {
        val fakeContext = ApplicationProvider.getApplicationContext<Context>()
        val product =
            Product(
                "[병천아우내] 모듬순대",
                11900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
            )
        val intent = ProductDetailActivity.newIntent(fakeContext, product)
        scenario = ActivityScenario.launch(intent)
    }

    @SuppressLint("CheckResult")
    @Test
    fun 상품의_이름_이미지와_가격이_표시된다() {
        onView(withId(R.id.iv_product_detail_image)).isDisplayed()
        onView(withId(R.id.tv_product_name)).matchText("[병천아우내] 모듬순대")
        onView(withId(R.id.tv_product_price)).matchText("11,900원")
    }

    @Test
    fun 장바구니_담기를_클릭하면_장바구니에_상품이_담긴다() {
        val product =
            Product(
                "[병천아우내] 모듬순대",
                11900,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/00fb05f8-cb19-4d21-84b1-5cf6b9988749.jpg",
            )
        onView(withId(R.id.btn_add_to_cart)).performClick()
        onView(withId(R.id.rv_shopping_cart_list)).isDisplayed()
        assert(DummyShoppingCart.products.contains(product))
    }
}
