package woowacourse.shopping.cart

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.PRODUCT_1
import woowacourse.shopping.PRODUCT_2
import woowacourse.shopping.R
import woowacourse.shopping.RecyclerViewMatcher
import woowacourse.shopping.RecyclerViewMatcher.Companion.hasDrawable
import woowacourse.shopping.RecyclerViewMatcher.Companion.withRecyclerView
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.fakeContext
import woowacourse.shopping.view.cart.CartActivity

class CartActivityTest {
    private lateinit var intent: Intent
    private lateinit var withRecyclerView: RecyclerViewMatcher

    @Before
    fun setUp() {
        intent =
            Intent(
                fakeContext,
                CartActivity::class.java,
            )

        withRecyclerView = withRecyclerView(R.id.rv_products_in_cart)

        CartRepositoryImpl.add(PRODUCT_1)
        CartRepositoryImpl.add(PRODUCT_2)

        ActivityScenario.launch<CartActivity>(intent)
    }

    @Test
    fun `장바구니에_담은_상품이름들이_보인다`() {
        onView(
            withRecyclerView.atPositionOnView(
                0,
                R.id.product_in_cart_title,
            ),
        ).check(matches(withText("[병천아우내] 모듬순대")))

        onView(
            withRecyclerView.atPositionOnView(
                1,
                R.id.product_in_cart_title,
            ),
        ).check(matches(withText("[빙그래] 요맘때 파인트 710mL 3종 (택1)")))
    }

    @Test
    fun `장바구니에_담은_상품가격들이_보인다`() {
        onView(withRecyclerView.atPositionOnView(0, R.id.product_in_cart_price)).check(
            matches(
                withText("11,900원"),
            ),
        )
        onView(withRecyclerView.atPositionOnView(1, R.id.product_in_cart_price)).check(
            matches(
                withText("5,000원"),
            ),
        )
    }

    @Test
    fun `장바구니에_담은_상품사진들이_보인다`() {
        onView(withRecyclerView.atPositionOnView(0, R.id.product_in_cart_image))
            .check(matches(hasDrawable()))

        onView(withRecyclerView.atPositionOnView(1, R.id.product_in_cart_image))
            .check(matches(hasDrawable()))
    }

    @Test
    fun `상품_삭제_버튼이_보인다`() {
        onView(withRecyclerView.atPositionOnView(0, R.id.remove_product_btn))
            .check(matches(isDisplayed()))

        onView(withRecyclerView.atPositionOnView(1, R.id.remove_product_btn))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `특정_상품을_삭제하면_화면에_보이지_않는다`() {
        onView(withRecyclerView.atPositionOnView(0, R.id.remove_product_btn))
            .perform(click())

        onView(withText("[병천아우내] 모듬순대"))
            .check(doesNotExist())

        onView(withText("11,900원"))
            .check(doesNotExist())
    }
}
