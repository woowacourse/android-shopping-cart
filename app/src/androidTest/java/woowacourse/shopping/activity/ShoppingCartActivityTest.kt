@file:Suppress("ktlint")

package woowacourse.shopping.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.data.DummyShoppingCart
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.view.uimodel.ProductUiModel
import woowacourse.shopping.matcher.RecyclerViewMatcher.Companion.withRecyclerView
import woowacourse.shopping.matcher.isDisplayed
import woowacourse.shopping.matcher.matchSize
import woowacourse.shopping.matcher.matchText
import woowacourse.shopping.matcher.performClick
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.view.uimodel.ShoppingCartItemUiModel
import woowacourse.shopping.fixture.TestShoppingCart
import woowacourse.shopping.mapper.toProductUiModel
import woowacourse.shopping.view.main.MainActivity

@Suppress("FunctionName")
class ShoppingCartActivityTest {
    val shoppingCartActivityScenario = ActivityScenario.launch(ShoppingCartActivity::class.java)


    @Test
    fun 장바구니에_담긴_상품의_목록이_표시된다() {
        onView(withId(R.id.shopping_cart_list))
            .isDisplayed()
        onView(
            withRecyclerView(R.id.shopping_cart_list)
                .atPositionOnView(0, R.id.tv_shopping_cart_item),
        ).matchText("[병천아우내] 모듬순대")
        onView(
            withRecyclerView(R.id.shopping_cart_list)
                .atPositionOnView(0, R.id.tv_price),
        ).matchText("11,900원")
    }

    @Test
    fun 장바구니_목록은_5개_단위로_페이지네이션_된다() {
        onView(withId(R.id.shopping_cart_list)).check(matchSize(5))
    }

    @Test
    fun 장바구니에서_원하는_상품을_삭제할_수_있다() {
        onView(
            withRecyclerView(R.id.shopping_cart_list)
                .atPositionOnView(0, R.id.iv_remove_item_product_icon),
        ).performClick()

        onView(
            withRecyclerView(R.id.shopping_cart_list)
                .atPositionOnView(0, R.id.tv_shopping_cart_item),
        ).check(matches(not(withText("[병천아우내] 모듬순대"))))

    }

    @Test
    fun 장바구니에서_수량을_조절하면_메인_화면에서도_반영된다() {

        //given
        onView(
            withRecyclerView(R.id.shopping_cart_list)
                .atPositionOnView(0, R.id.button_plus),
        ).performClick()

        //when
        shoppingCartActivityScenario.close()
        ActivityScenario.launch(MainActivity::class.java)

        //then
        onView(
            withRecyclerView(R.id.product_list)
                .atPositionOnView(1, R.id.text_quantity),
        ).matchText("2")


    }
}
