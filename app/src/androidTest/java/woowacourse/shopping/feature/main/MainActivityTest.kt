package woowacourse.shopping.feature.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToLastPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.RecyclerViewItemCountAssertion
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.imageUrl
import woowacourse.shopping.price
import woowacourse.shopping.title

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity>

    init {
        val productRepository: ProductRepository = ProductDummyRepository
        productRepository.deleteAll()
        repeat(30) {
            productRepository.save(imageUrl, "$title $it", price + it)
        }
        activityRule = ActivityScenarioRule(MainActivity::class.java)
    }

    @Test
    fun `상품_목록을_보여준다`() {
        onView(withId(R.id.rv_main_product)).check(matches(isDisplayed()))
    }

    @Test
    fun `상품의_제목이_보인다`() {
        onView(withId(R.id.rv_main_product))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText("$title 0"), isDisplayed()))))
    }

    @Test
    fun `상품의_가격이_보인다`() {
        val expected = "%,d원".format(price)
        onView(withId(R.id.rv_main_product))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(hasDescendant(allOf(withText(expected), isDisplayed()))))
    }

    @Test
    fun `상품_목록이_최상단일_때_더보기_버튼이_보이지_않는다`() {
        onView(withId(R.id.rv_main_product))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(R.id.btn_main_see_more))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun `20번째_상품이_화면_최하단에_닿기_전까지_더보기_버튼이_보이지_않는다`() {
        onView(withId(R.id.rv_main_product))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.btn_main_see_more))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun `20번째_상품이_최하단에_닿으면_더보기_버튼이_보여진다`() {
        onView(withId(R.id.rv_main_product))
            .perform(scrollToLastPosition<RecyclerView.ViewHolder>())
        onView(withId(R.id.btn_main_see_more))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun `상품_목록_최하단에서_더보기_버튼을_누르면_상품_10개가_더_보인다`() {
        onView(withId(R.id.rv_main_product))
            .perform(scrollToLastPosition<RecyclerView.ViewHolder>())

        onView(withId(R.id.btn_main_see_more))
            .perform(click())

        onView(withId(R.id.rv_main_product))
            .check(RecyclerViewItemCountAssertion(30))
    }
}
