package woowacourse.shopping.feature.cart

import android.content.pm.ActivityInfo
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.RecyclerViewItemCountAssertion
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.local.CartDummyRepository
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.data.product.remote.ProductService
import woowacourse.shopping.imageUrl
import woowacourse.shopping.price
import woowacourse.shopping.title

@RunWith(AndroidJUnit4::class)
class CartActivityTest {
    private val productRepository: ProductService = ProductDummyRepository
    private val cartRepository: CartRepository = CartDummyRepository

    @Before
    fun setUp() {
        productRepository.deleteAll()
        cartRepository.deleteAll()
    }

    @Test
    fun `장바구니_상품_목록이_보인다`() {
        ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.rv_cart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니의_상품_제목이_보인다`() {
        addCart(imageUrl, title, price)

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.tv_cart_product_title))
            .check(matches(withText(title)))
    }

    @Test
    fun `장바구니의_상품_가격이_보인다`() {
        addCart(imageUrl, title, price)

        ActivityScenario.launch(CartActivity::class.java)

        val expected = "%,d원".format(price)
        onView(withId(R.id.tv_cart_product_price))
            .check(matches(withText(expected)))
    }

    @Test
    fun `장바구니의_상품이_5개_이하인_경우_이전_페이지_버튼와_다음_페이지_버튼이_보이지_않는다`() {
        repeat(5) {
            addCart(imageUrl, title, price)
        }

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.btn_cart_previous_page))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.btn_cart_next_page))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun `장바구니의_상품이_6개_이상인_경우_이전_페이지_버튼이_비활성화_된다`() {
        repeat(6) {
            addCart(imageUrl, title, price)
        }

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.btn_cart_previous_page))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun `장바구니의_상품이_6개_이상인_경우_다음_페이지_버튼이_활성화_된다`() {
        repeat(6) {
            addCart(imageUrl, title, price)
        }

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.btn_cart_next_page))
            .check(matches(isEnabled()))
    }

    @Test
    fun `장바구니의_상품이_6개인_경우_다음_페이지로_이동하면_하나의_상품이_보인다`() {
        repeat(6) {
            addCart(imageUrl, title, price)
        }

        ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.btn_cart_next_page))
            .perform(click())

        onView(withId(R.id.rv_cart))
            .check(RecyclerViewItemCountAssertion(1))
    }

    @Test
    fun `장바구니에_10개의_상품이_있고_다음_페이지로_이동하면_다음_페이지_버튼이_비활성화_된다`() {
        repeat(10) {
            addCart(imageUrl, title, price)
        }

        ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.btn_cart_next_page))
            .perform(click())

        onView(withId(R.id.btn_cart_next_page))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun `장바구니에_10개의_상품이_있고_다음_페이지로_이동하면_이전_페이지_버튼이_활성화_된다`() {
        repeat(10) {
            addCart(imageUrl, title, price)
        }

        ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.btn_cart_next_page))
            .perform(click())

        onView(withId(R.id.btn_cart_previous_page))
            .check(matches(isEnabled()))
    }

    @Test
    fun `장바구니의_상품의_엑스_버튼을_누르면_장바구니_리스트에서_없어진다`() {
        addCart(imageUrl, title, price)

        ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.iv_cart_exit))
            .perform(click())

        onView(withId(R.id.rv_cart))
            .check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun `2페이지로_이동한_후_화면_회전_시_2페이지가_그대로_유지_된다`() {
        repeat(8) {
            addCart(imageUrl, title, price)
        }

        val activityScenario = ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.btn_cart_next_page))
            .perform(click())

        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.rv_cart))
            .check(RecyclerViewItemCountAssertion(3))
        onView(withId(R.id.tv_cart_page)).check(matches(withText("2")))
    }

    private fun addCart(
        productImageUrl: String,
        productTitle: String,
        productPrice: Int,
    ) {
        val productId = productRepository.save(productImageUrl, productTitle, productPrice)
        cartRepository.addProduct(productId)
    }
}
