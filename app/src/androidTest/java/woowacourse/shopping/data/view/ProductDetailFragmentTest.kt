package woowacourse.shopping.data.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.view.MainActivity
import woowacourse.shopping.view.detail.ProductDetailFragment

@RunWith(AndroidJUnit4::class)
class ProductDetailFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        val productDetailFragment =
            ProductDetailFragment().apply {
                arguments = ProductDetailFragment.createBundle(0L)
            }
        activityRule.scenario.onActivity { activity ->
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, productDetailFragment)
                .commitNow()
        }
    }

    @Test
    fun `선택한_상품의_이름이_보여져야_한다`() {
        onView(withId(R.id.tv_detail_product_name)).check(matches(withText("PET보틀-단지(400ml) 레몬청")))
    }

    @Test
    fun `선택한_상품의_가격이_보여져야_한다`() {
        onView(withId(R.id.tv_detail_product_price)).check(matches(withText("0원")))
    }
}
