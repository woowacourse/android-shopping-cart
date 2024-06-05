package woowacourse.shopping.presentation.ui.cart

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.local.RepositoryInjector
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.presentation.ui.fakeRepository

@RunWith(AndroidJUnit4::class)
class CartEntityActivityTest {
    @Before
    fun setUp() {
        RepositoryInjector.setInstance(fakeRepository)
    }

    @Test
    fun `장바구니에_아이템_리스트가_보여진다`() {
        ActivityScenario.launch(CartActivity::class.java)
        onView(ViewMatchers.withId(R.id.rv_carts))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `상품이_5개_이하면_페이지를_이동하는_버튼이_안보인다`() {
        repeat(5) { index ->
            RepositoryInjector.repository.saveCart(Cart(index.toLong(), 1))
        }
        ActivityScenario.launch(CartActivity::class.java)
        onView(ViewMatchers.withId(R.id.layout_page))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun `상품이_5개_초과면_페이지를_이동하는_버튼이_보여진다`() {
        repeat(6) { index ->
            RepositoryInjector.repository.saveCart(Cart(index.toLong(), 1))
        }
        ActivityScenario.launch(CartActivity::class.java)
        onView(ViewMatchers.withId(R.id.layout_page))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `상품이_5개_초과일때_6번째_아이템은_다음_페이지로_넘어가지_않으면_안보인다`() {
        repeat(6) { index ->
            RepositoryInjector.repository.saveCart(Cart(index.toLong(), 1))
        }
        ActivityScenario.launch(CartActivity::class.java)
        onView(withText("5")).check(doesNotExist())
    }

    @Test
    fun `상품이_5개_초과일때_6번째_아이템은_다음_페이지로_넘어가면_보여진다`() {
        repeat(6) { index ->
            RepositoryInjector.repository.saveCart(Cart(index.toLong(), 1))
        }
        ActivityScenario.launch(CartActivity::class.java)
        onView(withId(R.id.btn_right)).perform(click())
        onView(withText("5")).check(matches(isDisplayed()))
    }
}
