package woowacourse.shopping.presentation.detail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.util.ToastMatcher

class DetailActivityTest {
    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<DetailActivity> =
        ActivityScenarioRule(
            Intent(
                ApplicationProvider.getApplicationContext(),
                DetailActivity::class.java,
            ).putExtra(
                "extra_product_id",
                1L,
            ).putExtra(
                "extra_lastly_viewed",
                100L,
            ),
        )

    @Test
    fun `상품_상세_정보와_최근_본_상품을_표출한다`() {
        onView(withId(R.id.tv_product_title_detail)).check(matches(withText("사과1")))
        onView(withId(R.id.tv_price_value_detail)).check(matches(withText("0원")))
        onView(withId(R.id.tv_last_product_title)).check(matches(withText("사과100")))
    }

    @Test
    fun `수량을_증가하는_버튼을_클릭하면_상품_수와_가격이_증가한다`() {
        onView(withId(R.id.btn_addition)).perform(click())
        onView(withId(R.id.tv_quantity)).check(matches(withText("1")))
        onView(withId(R.id.tv_price_value_detail)).check(matches(withText("1,000원")))
    }

    @Test
    fun `수량을_감소하는_버튼을_클릭하면_상품_수와_가격이_감소한다`() {
        onView(withId(R.id.btn_addition)).perform(click())
        onView(withId(R.id.btn_addition)).perform(click())
        onView(withId(R.id.btn_subtraction)).perform(click())
        onView(withId(R.id.tv_quantity)).check(matches(withText("1")))
        onView(withId(R.id.tv_price_value_detail)).check(matches(withText("1,000원")))
    }

    @Test
    fun `수량_변경_후_장바구니에_담는_버튼을_클릭하면_스낵바가_표출된다`() {
        onView(withId(R.id.btn_addition)).perform(click())
        onView(withId(R.id.btn_addition)).perform(click())
        onView(withId(R.id.btn_subtraction)).perform(click())
        onView(withId(R.id.btn_add_to_cart_detail)).perform(click())
        onView(withText("장바구니에 상품을 추가했습니다."))
            .inRoot(ToastMatcher().apply { matches(isDisplayed()) })
    }
}
