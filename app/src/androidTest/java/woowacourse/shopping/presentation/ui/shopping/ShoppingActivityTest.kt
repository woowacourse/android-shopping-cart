package woowacourse.shopping.presentation.ui.shopping

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.presentation.ui.shopping.adapter.ShoppingViewHolder

@RunWith(AndroidJUnit4::class)
class ShoppingActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ShoppingActionActivity::class.java)

    @Test
    fun `상품_목록이_화면에_표시된다`() {
        onView(withId(R.id.rv_shopping))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `상품_목록의_맨_아래_20번째는_더보기_뷰홀더가_등장한다`() {
        onView(withId(R.id.rv_shopping)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20,
            ),
        )
            .check(
                matches(
                    matchViewHolderAtPosition(20, ShoppingViewHolder.LoadViewHolder::class.java),
                ),
            )
    }

    @Test
    fun `상품_목록_첫_번째는_상품이_보인다`() {
        onView(withId(R.id.rv_shopping)).check(
            matches(
                matchViewHolderAtPosition(0, ShoppingViewHolder.ProductViewHolder::class.java),
            ),
        )
    }

    @Test
    fun `처음엔_아이템이_20개이고_더보기를_클릭하면_40개가_된다`() {
        val recyclerViewInteraction = onView(withId(R.id.rv_shopping))
        val loadMoreViewInteraction = onView(withId(R.id.btn_show_more))
        // 20개
        recyclerViewInteraction.check { view, assertion ->
            val recyclerView = view as RecyclerView
            recyclerView.adapter?.itemCount shouldBe 20 + LOAD_MORE_OFFSET
        }
        recyclerViewInteraction.perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20,
            ),
        )
        loadMoreViewInteraction.perform(click())
        recyclerViewInteraction.check { view, assertion ->
            val recyclerView = view as RecyclerView
            recyclerView.adapter?.itemCount shouldBe 40 + LOAD_MORE_OFFSET
        }
    }

    private fun matchViewHolderAtPosition(
        position: Int,
        viewHolderClass: Class<out RecyclerView.ViewHolder>,
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Checking ViewHolder at position $position")
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is RecyclerView) return false
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                return viewHolder != null &&
                    viewHolderClass.isInstance(
                        viewHolder,
                    )
            }
        }
    }

    companion object {
        const val LOAD_MORE_OFFSET = 1
    }
}
