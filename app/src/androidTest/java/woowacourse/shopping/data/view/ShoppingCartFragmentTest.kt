package woowacourse.shopping.data.view

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.TestFixture
import woowacourse.shopping.data.db.cartItem.CartItemDao
import woowacourse.shopping.data.db.cartItem.CartItemDatabase
import woowacourse.shopping.view.MainActivity
import woowacourse.shopping.view.cart.ShoppingCartFragment
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class ShoppingCartFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var database: CartItemDatabase
    private lateinit var dao: CartItemDao
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        database = CartItemDatabase.getInstance(context)
        dao = database.cartItemDao()
        thread {
            dao.saveCartItem(TestFixture.makeCartItemEntity())
        }.join()

        activityRule.scenario.onActivity { activity ->
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ShoppingCartFragment())
                .commitNow()
        }
    }

    @After
    fun clearDB() {
        thread {
            database.clearAllTables()
        }.join()
    }

    @Test
    fun `장바구니_목록을_보여준다`() {
        onView(withId(R.id.rv_shopping_cart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니에_담은_상품_이름을_보여준다`() {
        Thread.sleep(1000)
        onView(withId(R.id.tv_cart_item_name)).check(matches(withText("아메리카노")))
    }

    @Test
    fun `장바구니에_담은_상품_가격을_보여준다`() {
        Thread.sleep(1000)
        onView(withId(R.id.tv_cart_item_price)).check(matches(withText("0원")))
    }

    @Test
    fun `다음_페이지의_데이터가_있을_때_다음_버튼을_클릭하면_페이지의_숫자가_증가한다`() {
        thread {
            dao.saveCartItem(TestFixture.makeCartItemEntity())
            dao.saveCartItem(TestFixture.makeCartItemEntity())
            dao.saveCartItem(TestFixture.makeCartItemEntity())
        }.join()
        activityRule.scenario.onActivity { activity ->
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ShoppingCartFragment())
                .commitNow()
        }

        onView(withId(R.id.btn_next)).perform(ViewActions.click())
        onView(withId(R.id.tv_page_count)).check(matches(withText("2")))
    }

    @Test
    fun `다음_페이지의_데이터가_없을_때_다음_버튼을_클릭하면_페이지의_숫자가_증가하지_않는다`() {
        onView(withId(R.id.btn_next)).perform(ViewActions.click())

        onView(withId(R.id.tv_page_count)).check(matches(withText("1")))
    }
}
