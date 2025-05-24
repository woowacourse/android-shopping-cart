@file:Suppress("ktlint")

package woowacourse.shopping.activity

import android.annotation.SuppressLint
import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.matcher.isDisplayed
import woowacourse.shopping.matcher.matchText
import woowacourse.shopping.matcher.performClick
import woowacourse.shopping.view.detail.ProductDetailActivity
import woowacourse.shopping.view.uimodel.ProductUiModel
import woowacourse.shopping.fixture.TestShoppingCart
import woowacourse.shopping.fixture.TestProducts
import woowacourse.shopping.mapper.toProduct
import woowacourse.shopping.mapper.toProductUiModel
import woowacourse.shopping.matcher.RecyclerViewMatcher.Companion.withRecyclerView

class ProductDetailActivityTest {
    private lateinit var scenario: ActivityScenario<ProductDetailActivity>
    val product = TestProducts.productUiModels[0]

    @get:Rule
    val productDetailActivityScenarioRule = ActivityScenarioRule(ProductDetailActivity::class.java)

    @Before
    fun setUp() {
        val fakeContext = ApplicationProvider.getApplicationContext<Context>()
        val intent = ProductDetailActivity.newIntent(fakeContext, product.toProductUiModel())
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
        onView(withId(R.id.btn_add_to_cart)).performClick()
        onView(withId(R.id.shopping_cart_list)).isDisplayed()
        onView(
            withRecyclerView(R.id.shopping_cart_list)
                .atPositionOnView(
                0,
                R.id.text_quantity,
            )
        ).matchText("2")

    }

    @After
    fun tearDown() {
        DummyShoppingCart.items.remove(
            ShoppingCartItem(
                product.id,
                product,
                2
            )
        )
    }
}
