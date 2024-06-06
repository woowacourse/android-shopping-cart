package woowacourse.shopping.ui.cart

import android.content.pm.ActivityInfo
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.FakeCartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.firstProduct
import woowacourse.shopping.hasSizeRecyclerView

@RunWith(AndroidJUnit4::class)
class CartActivityTest {
    @Test
    fun `장바구니에_상품이_없는_경우_장바구니가_비어있다는_화면이_보인다`() {
        CartRepository.setInstance(FakeCartRepository())

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.tv_empty_cart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니에_상품이_있는_경우_상품_목록이_보인다`() {
        setUpCart(1)

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.rv_cart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니의_상품_제목이_보인다`() {
        setUpCart(1)

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.rv_cart))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText(firstProduct.title), isDisplayed()))))
    }

    @Test
    fun `장바구니의_상품_가격이_보인다`() {
        setUpCart(1)

        ActivityScenario.launch(CartActivity::class.java)

        val expected = "%,d원".format(firstProduct.price)
        onView(withId(R.id.rv_cart))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText(expected), isDisplayed()))))
    }

    @Test
    fun `장바구니의_상품이_5개_이하인_경우_이전_페이지_버튼와_다음_페이지_버튼이_보이지_않는다`() {
        setUpCart(5)

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.btn_cart_previous_page))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.btn_cart_next_page))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun `장바구니의_상품이_6개_이상인_경우_이전_페이지_버튼이_비활성화_된다`() {
        setUpCart(6)

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.btn_cart_previous_page))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun `장바구니의_상품이_6개_이상인_경우_다음_페이지_버튼이_활성화_된다`() {
        setUpCart(6)

        ActivityScenario.launch(CartActivity::class.java)

        onView(withId(R.id.btn_cart_next_page))
            .check(matches(isEnabled()))
    }

    @Test
    fun `장바구니의_상품이_6개인_경우_다음_페이지로_이동하면_하나의_상품이_보인다`() {
        setUpCart(6)

        ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.btn_cart_next_page))
            .perform(click())

        onView(withId(R.id.rv_cart))
            .hasSizeRecyclerView(1)
    }

    @Test
    fun `장바구니에_10개의_상품이_있고_다음_페이지로_이동하면_다음_페이지_버튼이_비활성화_된다`() {
        setUpCart(10)

        ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.btn_cart_next_page))
            .perform(click())

        onView(withId(R.id.btn_cart_next_page))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun `장바구니에_10개의_상품이_있고_다음_페이지로_이동하면_이전_페이지_버튼이_활성화_된다`() {
        setUpCart(10)

        ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.btn_cart_next_page))
            .perform(click())

        onView(withId(R.id.btn_cart_previous_page))
            .check(matches(isEnabled()))
    }

    @Test
    fun `장바구니의_상품의_엑스_버튼을_누르면_장바구니_리스트에서_없어진다`() {
        setUpCart(1)

        ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.iv_cart_exit))
            .perform(click())

        onView(withId(R.id.rv_cart))
            .hasSizeRecyclerView(0)
    }

    @Test
    fun `장바구니에_6개의_상품이_있고_2페이지일_때_화면을_회전해도_2페이지가_보인다`() {
        setUpCart(6)

        val activityScenario = ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.btn_cart_next_page))
            .perform(click())

        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.rv_cart))
            .hasSizeRecyclerView(1)
    }

    private fun setUpCart(cartSize: Int) {
        val products = ProductRepository.getInstance().findRange(0, cartSize)
        CartRepository.setInstance(FakeCartRepository())
        products.forEach { CartRepository.getInstance().increaseQuantity(it.id) }
    }
}
