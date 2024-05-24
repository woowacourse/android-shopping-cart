package woowacourse.shopping.presentation.cart

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.datasourceimpl.DefaultCart
import woowacourse.shopping.db.cart.CartDao
import woowacourse.shopping.db.cart.CartDatabase
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CartActivityTest {
    private lateinit var cartDao: CartDao
    private lateinit var cartDatabase: CartDatabase
    private lateinit var defaultCart: DefaultCart

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(CartActivity::class.java)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        cartDatabase =
            inMemoryDatabaseBuilder(
                context, CartDatabase::class.java,
            ).build()
        cartDao = cartDatabase.cartDao()

        defaultCart = DefaultCart(cartDatabase)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        cartDatabase.close()
    }

    @Test
    fun `장바구니_상품_목록과_페이지_내비게이터가_표출된다`() {
        repeat(6) {
            defaultCart.addCart(it + 1L, 1)
        }
        onView(withId(R.id.rv_cart)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_previous_page)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_next_page)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_current_page)).check(matches(withText("1")))
    }

    @Test
    fun `다음_페이지_이동_버튼을_클릭하면_새로운_장바구니_상품들이_표출된다`() {
        repeat(6) {
            defaultCart.addCart(it + 1L, 1)
        }
        onView(withId(R.id.btn_next_page)).perform(click())
        onView(withId(R.id.rv_cart)).check(
            matches(
                matchViewHolderAtPosition(
                    0,
                    RecyclerView.ViewHolder::class.java,
                ),
            ),
        )
    }

    @Test
    fun `이전_페이지_이동_버튼을_클릭하면_새로운_장바구니_상품들이_표출된다`() {
        repeat(6) {
            defaultCart.addCart(it + 1L, 1)
        }
        onView(withId(R.id.btn_next_page)).perform(click())
        onView(withId(R.id.btn_previous_page)).perform(click())
        onView(withId(R.id.rv_cart)).check(
            matches(
                matchViewHolderAtPosition(
                    0,
                    RecyclerView.ViewHolder::class.java,
                ),
            ),
        )
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
}
