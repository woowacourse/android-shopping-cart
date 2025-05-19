package woowacourse.shopping.feature.cart

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test
import woowacourse.shopping.R

@Suppress("ktlint:standard:function-naming")
class CartActivityTest {
    @Test
    fun 장바구니_화면_레이아웃_테스트() {
        ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.rv_goods)).check(matches(isDisplayed()))
    }
}
