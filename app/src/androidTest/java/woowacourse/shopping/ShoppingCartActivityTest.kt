@file:Suppress("ktlint")

package woowacourse.shopping

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.matcher.RecyclerViewMatcher.Companion.withRecyclerView
import woowacourse.shopping.matcher.isDisplayed
import woowacourse.shopping.matcher.matchSize
import woowacourse.shopping.matcher.matchText
import woowacourse.shopping.matcher.performClick
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

@Suppress("FunctionName")
class ShoppingCartActivityTest {
    @get:Rule
    val shoppingCartActivityScenarioRule = ActivityScenarioRule(ShoppingCartActivity::class.java)

    @Test
    fun 장바구니에_담긴_상품의_목록이_표시된다() {
        onView(withId(R.id.shopping_cart_list))
            .isDisplayed()
        onView(
            withRecyclerView(R.id.shopping_cart_list)
                .atPositionOnView(0, R.id.tv_shopping_cart_item),
        ).matchText("[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트")
        onView(
            withRecyclerView(R.id.shopping_cart_list)
                .atPositionOnView(0, R.id.tv_price),
        ).matchText("42,000원")
    }

    @Test
    fun 장바구니_목록은_5개_단위로_페이지네이션_된다() {
        onView(withId(R.id.shopping_cart_list)).check(matchSize(5))
    }

    @Test
    fun 장바구니에서_원하는_상품을_삭제할_수_있다() {
        val expectedDeleteProduct =
            Product(
                "[런던베이글뮤지엄] 베이글 6개 & 크림치즈 3개 세트",
                42000,
                "https://product-image.kurly.com/hdims/resize/%5E%3E360x%3E468/cropcenter/360x468/quality/85/src/product/image/3c68d05b-d392-4a38-8637-a25068220fa4.jpg",
            )
        onView(
            withRecyclerView(R.id.shopping_cart_list)
                .atPositionOnView(0, R.id.iv_remove_item_product_icon),
        ).performClick()

        assertThat(DummyShoppingCart.products).contains(expectedDeleteProduct)
    }
}
