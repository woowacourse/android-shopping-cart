package woowacourse.shopping.presentation.detail

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {
//    @get:Rule
//    val scenarioRule =
//        ActivityScenarioRule<DetailActivity>(
//            DetailActivity.newIntent(ApplicationProvider.getApplicationContext(), 1),
//        )
//
//    @Test
//    fun `선택한_상품의_정보를_표출한다`() {
//        onView(withId(R.id.iv_product_detail)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_product_title_detail)).check(matches(withText("사과")))
//        onView(withId(R.id.tv_price_value_detail)).check(matches(withText("1,000원")))
//    }
}
