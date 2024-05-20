package woowacourse.shopping.presentaion.ui.shopping

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.presentation.ui.shopping.ShoppingActivity
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewHolder

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
        onView(withId(R.id.shopping_action)).check(matches(isDisplayed()))
    }

    @Test
    fun `초기_화면에는_더보기_버튼이_보이지_않는다`() {
        onView(withId(R.id.btn_load_more)).check(matches(not(isDisplayed())))
    }

    @Test
    fun `스크롤이_가장_아래로_내려가면_더보기_버튼이_보인다`() {
        onView(withId(R.id.rv_product_list))
            .perform(RecyclerViewActions.scrollToPosition<ShoppingViewHolder>(9))

        Thread.sleep(1000)

        onView(withId(R.id.btn_load_more)).check(matches(isDisplayed()))
    }
}
