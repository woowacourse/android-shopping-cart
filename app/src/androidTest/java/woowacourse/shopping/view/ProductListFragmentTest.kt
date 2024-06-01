package woowacourse.shopping.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.view.products.ProductsListFragment
import woowacourse.shopping.view.products.adapter.viewholder.ProductListViewHolder

@RunWith(AndroidJUnit4::class)
class ProductListFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity { activity ->
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProductsListFragment())
                .commitNow()
        }
    }

    @Test
    fun `상품_목록을_보여준다`() {
        onView(withId(R.id.rv_products))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `데이터가_모두_보여지지_않은_경우_더보기_버튼은_보여지지_않아야_한다`() {
        onView(withId(R.id.rv_products))
            .perform(RecyclerViewActions.scrollToPosition<ProductListViewHolder>(10))
            .check(matches(atPosition(10, not(hasDescendant(withId(R.id.btn_more_product))))))
    }

    @Test
    fun `데이터가_특정_개수만큼_로드된_경우_더보기_버튼이_보여진다`() {
        onView(withId(R.id.rv_products))
            .perform(RecyclerViewActions.scrollToPosition<ProductListViewHolder>(20))
            .check(matches(atPosition(20, hasDescendant(withId(R.id.btn_more_product)))))
    }

    private fun atPosition(
        position: Int,
        itemMatcher: Matcher<View>,
    ): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder =
                    view.findViewHolderForAdapterPosition(position)
                        ?: // has no item on such position
                        return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}
