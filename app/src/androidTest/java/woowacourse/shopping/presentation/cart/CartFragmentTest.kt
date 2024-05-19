package woowacourse.shopping.presentation.cart

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartRepositoryInjector
import woowacourse.shopping.data.cart.FakeCartRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.product
import woowacourse.shopping.products
import woowacourse.shopping.util.clickChildViewWithId
import woowacourse.shopping.util.testApplicationContext
import woowacourse.shopping.util.withItemCount


@RunWith(AndroidJUnit4::class)
class CartFragmentTest {

    @After
    fun tearDown() {
        CartRepositoryInjector.clear()
    }

    @Test
    @DisplayName("장바구니가 비어있으면, 비어 있다는 문구가 보인다")
    fun test1() {
        // given
        startScenarioWith()
        val emptyText = testApplicationContext.getString(R.string.cart_empty)
        // when & then
        onView(withText(containsString(emptyText)))
            .check(matches(isDisplayed()))
    }

    @Test
    @DisplayName("상품이 있으면, 비어있는 문구가 안보인다")
    fun test2() {
        // given
        startScenarioWith(product())
        val emptyText = testApplicationContext.getString(R.string.cart_empty)
        // when & then
        onView(withText(containsString(emptyText)))
            .check(matches(not(isDisplayed())))
    }

    @Test
    @DisplayName("현재 페이지가 1이고 장바구니에 상품이 5개 있으면, 다음 페이지 버튼이 비활성화 되어 있다.")
    fun test3() {
        // given
        startScenarioWith(products(5))
        // when & then
        onView(withId(R.id.tv_plus_page))
            .check(matches(isNotEnabled()))
    }

    @Test
    @DisplayName("현재 페이지가 1이고 장바구니에 상품이 6개 있으면, 다음 페이지 버튼이 활성화 되어 있다.")
    fun test4() {
        // given
        startScenarioWith(products(6))
        // when & then
        onView(withId(R.id.tv_plus_page))
            .check(matches(isEnabled()))
    }

    @Test
    @DisplayName("현재 페이지가 1이고 장바구니에 상품이 6개 있을 때, 다음 페이지 버튼을 누르면, 1개의 상품이 보인다")
    fun test5() {
        // given
        val expectCount = 1
        val expectProductTitle = "6"
        startScenarioWith(products(6))
        // when
        onView(withId(R.id.tv_plus_page))
            .perform(ViewActions.click())
        // then
        onView(withId(R.id.rv_shopping_cart))
            .check(
                matches(
                    hasDescendant(
                        withText(
                            containsString(expectProductTitle)
                        )
                    )
                )
            ).check(withItemCount(expectCount))
    }

    @Test
    @DisplayName(
        "현재 페이지가 1이고 장바구니에 상품이 6개 있을 때, 3번째 상품을 삭제 하면 " +
                "6 번째 상품이 현재 페이지에 보인다"
    )
    fun test6() {
        // given
        val expectProductTitle = "6"
        val deletePosition = 2
        val addedPosition = 4
        startScenarioWith(products(6))
        // when
        onView(withId(R.id.rv_shopping_cart)).perform(
            RecyclerViewActions.scrollToHolder(
                CoreMatchers.instanceOf(CartAdapter.CartViewHolder::class.java),
            ).atPosition(deletePosition)
        )
        onView(withId(R.id.rv_shopping_cart)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CartAdapter.CartViewHolder>(
                deletePosition,
                clickChildViewWithId(R.id.iv_shooping_cart_delete)
            )
        )
        // then
        onView(withId(R.id.rv_shopping_cart)).check(withItemCount(5))
        // when
        onView(withId(R.id.rv_shopping_cart)).perform(
            RecyclerViewActions.scrollToHolder(
                CoreMatchers.instanceOf(CartAdapter.CartViewHolder::class.java),
            ).atPosition(addedPosition)
        ).check(
            matches(
                hasDescendant(
                    withText(
                        containsString(expectProductTitle)
                    )
                )
            )
        )
    }

    @Test
    @DisplayName(
        "현재 페이지가 1이고 장바구니에 상품이 5개 있을 때, 3번째 상품을 삭제 하면 " +
                "4개가 보인다"
    )
    fun test7() {
        // given
        val deletePosition = 2
        val expectCount = 4
        startScenarioWith(products(5))
        // when
        onView(withId(R.id.rv_shopping_cart)).perform(
            RecyclerViewActions.scrollToHolder(
                CoreMatchers.instanceOf(CartAdapter.CartViewHolder::class.java),
            ).atPosition(deletePosition)
        )
        onView(withId(R.id.rv_shopping_cart)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CartAdapter.CartViewHolder>(
                deletePosition,
                clickChildViewWithId(R.id.iv_shooping_cart_delete)
            )
        )
        // then
        onView(withId(R.id.rv_shopping_cart)).check(withItemCount(expectCount))
    }

    private fun startScenarioWith(vararg products: Product = emptyArray()) {
        val fakeCartRepository = FakeCartRepository(Cart(products.toList()))
        CartRepositoryInjector.setCartRepository(fakeCartRepository)
        launchFragmentInContainer<CartFragment>()
    }

    private fun startScenarioWith(products: List<Product>) {
        val fakeCartRepository = FakeCartRepository(Cart(products))
        CartRepositoryInjector.setCartRepository(fakeCartRepository)
        launchFragmentInContainer<CartFragment>()
    }
}