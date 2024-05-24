package woowacourse.shopping.view.shopping

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R

@RunWith(AndroidJUnit4::class)
class ShoppingActivityTest {
    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            ShoppingActivity::class.java,
        )

    @get:Rule
    val activityRule: ActivityScenarioRule<ShoppingActivity> =
        ActivityScenarioRule<ShoppingActivity>(intent)

    @Test
    fun `화면에_상품_목록이_나타난다`() {
        onView(withId(R.id.rv_product_list)).check(matches(isDisplayed()))
    }

    @Test
    fun `화면에_장바구니_버튼이_보인다`() {
        onView(withId(R.id.btn_shopping_cart)).check(matches(isDisplayed()))
    }
}
