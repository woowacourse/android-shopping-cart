package woowacourse.shopping.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
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
        onView(withId(R.id.tv_detail_product_name)).check(matches(withText("쿨피스 프리미엄 복숭아맛")))
    }

    @Test
    fun `선택한_상품의_가격이_보여져야_한다`() {
        onView(withId(R.id.tv_detail_product_price)).check(matches(withText("2,000원")))
    }

    @Test
    fun `장바구니_담기_버튼이_보여져야_한다`() {
        onView(withId(R.id.btn_add_cart)).check(matches(isDisplayed()))
    }

    @Test
    fun `창_닫기_버튼이_보여져야_한다`() {
        onView(withId(R.id.btn_close)).check(matches(isDisplayed()))
    }

    @Test
    fun `버튼을_누르면_장바구니에_담은_상품의_수량을_증가시킬_수_있다`() {
        onViewClick(R.id.btn_right)

        onView(withId(R.id.tv_count)).check(matches(withText("2")))
    }

    @Test
    fun `버튼을_누르면_장바구니에_담은_상품의_수량을_감소시킬_수_있다`() {
        onViewClick(R.id.btn_right)
        onViewClick(R.id.btn_right)

        onViewClick(R.id.btn_left)

        onView(withId(R.id.tv_count)).check(matches(withText("2")))
    }

    @Test
    fun `장바구니에_담은_상품의_수량이_1일_경우에는_버튼을_눌러도_수량을_감소시킬_수_없다`() {
        onViewClick(R.id.btn_left)

        onView(withId(R.id.tv_count)).check(matches(withText("1")))
    }
}
