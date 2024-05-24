package woowacourse.shopping.view

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.TestFixture.makeCartItemEntity
import woowacourse.shopping.data.db.cartItem.CartItemDao
import woowacourse.shopping.data.db.cartItem.CartItemDatabase
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
            dao.saveCartItem(
                makeCartItemEntity(
                    productId = 0L,
                    name = "아메리카노",
                    quantity = 1,
                    price = 1_000,
                ),
            )
        }.join()
    }

    private fun launchFragment() {
        activityRule.scenario.onActivity { activity ->
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ShoppingCartFragment())
                .commitNow()
        }
    }

    @After
    fun clearDB() {
        database.clearAllTables()
    }

    @Test
    fun `장바구니_목록을_보여준다`() {
        launchFragment()

        onView(withId(R.id.rv_shopping_cart))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `장바구니에_담은_상품_이름을_보여준다`() {
        launchFragment()

        onView(withId(R.id.tv_cart_item_name)).check(matches(withText("아메리카노")))
    }

    @Test
    fun `장바구니에_담은_상품_가격을_보여준다`() {
        launchFragment()

        onView(withId(R.id.tv_cart_item_price)).check(matches(withText("1,000원")))
    }

    @Test
    fun `첫_페이지_이후_다음_페이지의_데이터가_없을_때_페이지_선택_버튼이_보이지_않는다`() {
        launchFragment()

        onView(withId(R.id.btn_next)).check(matches(not(isDisplayed())))
    }

    @Test
    fun `다음_페이지의_데이터가_있을_때_다음_버튼을_클릭하면_페이지의_숫자가_증가한다`() {
        thread {
            dao.saveCartItem(makeCartItemEntity(1L, "상품1", 2))
            dao.saveCartItem(makeCartItemEntity(2L, "상품2", 1))
            dao.saveCartItem(makeCartItemEntity(3L, "상품3", 2))
        }.join()

        launchFragment()

        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(R.id.tv_page_count)).check(matches(withText("2")))
    }

    @Test
    fun `다음_페이지의_데이터가_없을_때_다음_버튼을_클릭하면_페이지의_숫자가_증가하지_않는다`() {
        thread {
            dao.saveCartItem(makeCartItemEntity(1L, "상품1", 2))
            dao.saveCartItem(makeCartItemEntity(2L, "상품2", 1))
            dao.saveCartItem(makeCartItemEntity(3L, "상품3", 2))
            dao.saveCartItem(makeCartItemEntity(4L, "상품4", 2))
        }.join()

        launchFragment()

        onView(withId(R.id.btn_next)).perform(click())
        onView(withId(R.id.btn_next)).perform(click())

        onView(withId(R.id.tv_page_count)).check(matches(withText("2")))
    }

    @Test
    fun `장바구니에_담은_상품의_수량을_보여준다`() {
        launchFragment()

        onView(withId(R.id.tv_count)).check(matches(withText("1")))
    }

    @Test
    fun `버튼을_누르면_장바구니에_담은_상품의_수량을_증가시킬_수_있다`() {
        launchFragment()

        onView(withId(R.id.btn_right)).perform(click())

        onView(withId(R.id.tv_count)).check(matches(withText("2")))
    }

    @Test
    fun `버튼을_누르면_장바구니에_담은_상품의_수량을_감소시킬_수_있다`() {
        launchFragment()
        onView(withId(R.id.btn_right)).perform(click())
        onView(withId(R.id.btn_right)).perform(click())

        onView(withId(R.id.btn_left)).perform(click())

        onView(withId(R.id.tv_count)).check(matches(withText("2")))
    }

    @Test
    fun `장바구니에_담은_상품의_수량이_1일_경우에는_버튼을_눌러도_수량을_감소시킬_수_없다`() {
        launchFragment()

        onView(withId(R.id.btn_left)).perform(click())

        onView(withId(R.id.tv_count)).check(matches(withText("1")))
    }
}
