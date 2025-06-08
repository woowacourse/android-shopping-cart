@file:Suppress("ktlint")

package woowacourse.shopping.view.shoppingcart

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.util.isDisplayed

@Suppress("FunctionName")
class ShoppingCartActivityTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(ShoppingCartActivity::class.java)

    @Test
    fun 장바구니에_담긴_상품의_목록이_표시된다() {
        onView(withId(R.id.rv_shopping_cart_list)).isDisplayed()
    }
}
