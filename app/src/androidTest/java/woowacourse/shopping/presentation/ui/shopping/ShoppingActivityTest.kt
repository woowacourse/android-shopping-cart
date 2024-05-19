package woowacourse.shopping.presentation.ui.shopping

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
    @get:Rule
    val activityRule: ActivityScenarioRule<ShoppingActivity> =
        ActivityScenarioRule<ShoppingActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                ShoppingActivity::class.java,
            ),
        )

    @Test
    fun `화면에_상품_목록이_나타난다`() {
        onView(withId(R.id.rv_product_list)).check(matches(isDisplayed()))
    }

    @Test
    fun `화면에_장바구니_버튼이_보인다`() {
        onView(withId(R.id.shopping_action)).check(matches(isDisplayed()))
    }
}
