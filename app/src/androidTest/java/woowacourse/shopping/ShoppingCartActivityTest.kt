@file:Suppress("ktlint")

package woowacourse.shopping

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.matcher.isDisplayed
import woowacourse.shopping.matcher.matchSize
import woowacourse.shopping.view.shoppingcart.ShoppingCartActivity

@Suppress("FunctionName")
class ShoppingCartActivityTest {
    @get:Rule
    val shoppingCartActivityScenarioRule = ActivityScenarioRule(ShoppingCartActivity::class.java)

    @Test
    fun 장바구니에_담긴_상품의_목록이_표시된다() {
        onView(withId(R.id.rv_shopping_cart_list)).isDisplayed()
    }

    @Test
    fun 장바구니_목록은_5개_단위로_페이지네이션_된다() {
        onView(withId(R.id.rv_shopping_cart_list)).check(matchSize(5))
    }
}
