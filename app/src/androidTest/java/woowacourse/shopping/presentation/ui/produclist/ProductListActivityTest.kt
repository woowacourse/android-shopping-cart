package woowacourse.shopping.presentation.ui.produclist

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToLastPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.presentation.ui.productlist.ProductListActivity
import woowacourse.shopping.presentation.ui.productlist.adapter.ProductListAdapter
import woowacourse.shopping.presentation.ui.utils.RecyclerViewItemCountAssertion

@RunWith(AndroidJUnit4::class)
class ProductListActivityTest {
    @get:Rule
    val activityRule: ActivityScenarioRule<ProductListActivity> =
        ActivityScenarioRule<ProductListActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                ProductListActivity::class.java,
            ),
        )

    @Test
    fun `20개의_상품_목록이_있을_때_20개의_상품_목록으로_스크롤을_하면_더보기_버튼이_보인다`() {
        // Given
        val recyclerView = onView(withId(R.id.rv_product_list))

        // When
        recyclerView.perform(scrollToPosition<ProductListAdapter.ProductListViewHolder>(20))

        Thread.sleep(1000)

        // then
        onView(withId(R.id.tv_show_more_products)).check(
            matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)),
        )

        Thread.sleep(1000)

        onView(withId(R.id.tv_empty_show_more)).check(
            matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)),
        )
    }

    @Test
    fun `더_볼_상품_목록이_없을_때_더보기_버튼이_보이지_않는다`() {
        // Given
        val recyclerView = onView(withId(R.id.rv_product_list))
        recyclerView.perform(scrollToPosition<ProductListAdapter.ProductListViewHolder>(20))
        Thread.sleep(1000)

        // When
        onView(withId(R.id.tv_show_more_products)).perform(scrollTo()).perform(click())
        recyclerView.perform(scrollToLastPosition<ProductListAdapter.ProductListViewHolder>())
        Thread.sleep(1000)
        onView(withId(R.id.tv_show_more_products)).perform(scrollTo()).perform(click())
        recyclerView.perform(scrollToLastPosition<ProductListAdapter.ProductListViewHolder>())

        Thread.sleep(1000)
        // then
        onView(withId(R.id.tv_show_more_products)).check(
            matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)),
        )
        onView(withId(R.id.tv_empty_show_more)).check(
            matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)),
        )
    }

    @Test
    fun `20개의_상품_목록이_있을_때_더보기_버튼을_누르면_상품이_20개_추가되어_보여진다`() {
        // Given
        Thread.sleep(1000)

        val recyclerView = onView(withId(R.id.rv_product_list))
        Thread.sleep(1000)

        recyclerView.perform(scrollToPosition<ProductListAdapter.ProductListViewHolder>(20))
        Thread.sleep(1000)

        // When
        onView(withId(R.id.tv_show_more_products)).perform(scrollTo()).perform(click())
        Thread.sleep(1000)

        // then
        recyclerView.check(RecyclerViewItemCountAssertion(41))
    }
}
