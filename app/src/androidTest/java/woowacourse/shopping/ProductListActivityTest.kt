package woowacourse.shopping

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.productlist.ProductListActivity

class ProductListActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ProductListActivity::class.java)

    @Test
    fun `화면에_상품_목록이_나타난다`() {
        onView(withId(R.id.rcv_product_list)).check(matches(isDisplayed()))
    }

    @Test
    fun `상품_목록에는_상품의_이름과_가격이_나타난다`() {
        repeat(4) {
            onView(withId(R.id.rcv_product_list))
                .check(matches(withViewAtPosition(it, isDisplayed())))
                .perform(checkChildViewTextIsDisplayed(R.id.tv_product_list_name))
                .perform(checkChildViewTextIsDisplayed(R.id.tv_product_list_price))
        }
    }

    @Test
    fun `상품_목록에는_상품의_이미지가_나타난다`() {
        Thread.sleep(5000)
        repeat(4) {
            onView(withId(R.id.rcv_product_list))
                .check(matches(withViewAtPosition(it, isDisplayed())))
                .perform(checkChildViewImageIsDisplayed(R.id.iv_product_item))
        }
    }

    private fun checkChildViewTextIsDisplayed(id: Int) =
        object : ViewAction {
            override fun getDescription(): String {
                return "Check if text(id: $id) of child view is displayed"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(View::class.java))
            }

            override fun perform(
                uiController: UiController,
                view: View,
            ) {
                val textView = view.findViewById<TextView>(id)
                assertThat(textView.text).isNotBlank()
            }
        }

    private fun checkChildViewImageIsDisplayed(id: Int) =
        object : ViewAction {
            override fun getDescription(): String {
                return "Check if image(id: $id) of child view is displayed"
            }

            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(View::class.java))
            }

            override fun perform(
                uiController: UiController,
                view: View,
            ) {
                val imageView = view.findViewById<ImageView>(id)
                assertThat(imageView).isNotNull()
                assertThat(imageView.drawable).isNotNull()
            }
        }

    private fun withViewAtPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Item at position $position in ViewGroup(RecyclerView)")
            }

            override fun matchesSafely(view: View): Boolean {
                return when (view) {
                    is RecyclerView -> {
                        val viewHolder = view.findViewHolderForAdapterPosition(position)
                            ?: return false
                        itemMatcher.matches(viewHolder.itemView)
                    }
                    is ListView -> {
                        val listItem = view.getChildAt(position)
                            ?: return false
                        itemMatcher.matches(listItem)
                    }
                    is ViewGroup -> {
                        val childView = view.getChildAt(position)
                            ?: return false
                        itemMatcher.matches(childView)
                    }
                    else -> false
                }
            }
        }
    }
}
