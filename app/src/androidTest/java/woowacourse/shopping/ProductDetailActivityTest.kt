package woowacourse.shopping

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.product.Money
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.ui.productdetail.ProductDetailActivity

class ProductDetailActivityTest {
    private val product = Product(1, "https://image.msscdn.net/thumbnails/images/goods_img/20220215/2359611/2359611_17024446698621_big.jpg?w=1200", "테스트 상품", Money(1000))
    private lateinit var scenario: ActivityScenario<ProductDetailActivity>

    @BeforeEach
    fun setUp() {
        val intent = ProductDetailActivity.newIntent(ApplicationProvider.getApplicationContext(), product)
        scenario = ActivityScenario.launch(intent)
    }

    @Test
    fun 상세_페이지의_이미지가_보인다() {
        onView(withId(R.id.iv_detail_image))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 상세_페이지의_상품명이_보인다() {
        onView(withId(R.id.tv_detail_product_title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 인텐트로_받은_상품의_이름과_일치한다() {
        onView(withId(R.id.tv_detail_product_title))
            .check(matches(withText("테스트 상품")))
    }

    @Test
    fun 상세_페이지의_상품의_가격이_보인다() {
        onView(withId(R.id.tv_detail_product_price))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 인텐트로_받은_상품의_가격과_일치한다() {
        onView(withId(R.id.tv_detail_product_price))
            .check(matches(withText("1,000원")))
    }

    @Test
    fun 상세_페이지의_장바구니_담기_버튼이_보인다() {
        onView(withId(R.id.btn_detail_add_cart))
            .check(matches(isDisplayed()))
    }
}
