package woowacourse.shopping.presentation.cart

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.cart.CartRepositoryInjector
import woowacourse.shopping.data.cart.FakeCartRepository
import woowacourse.shopping.domain.entity.Cart
import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.fixtures.fakeProduct
import woowacourse.shopping.fixtures.fakeProducts
import woowacourse.shopping.util.matchDescendantSoftly
import woowacourse.shopping.util.performClickHolderAt
import woowacourse.shopping.util.performScrollToHolder
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
    fun empty_test() {
        // given
        startScenarioWith()
        val emptyText = testApplicationContext.getString(R.string.cart_empty)
        // when & then
        onView(withText(containsString(emptyText)))
            .check(matches(isDisplayed()))
    }

    @Test
    @DisplayName("상품이 있으면, 비어있는 문구가 안보인다")
    fun display_test() {
        // given
        startScenarioWith(fakeProduct())
        val emptyText = testApplicationContext.getString(R.string.cart_empty)
        // when & then
        onView(withText(containsString(emptyText)))
            .check(matches(not(isDisplayed())))
    }

    @Test
    @DisplayName("상품이 수량이 1개일 때, 증가 시키면 2개가 된다")
    fun increase_count_test() {
        // given
        startScenarioWith(fakeProduct())
        // when & then
        onView(withId(R.id.rv_shopping_cart)).performClickHolderAt<CartAdapter.CartViewHolder>(
            0,
            R.id.btn_increase_product,
        )
        Thread.sleep(100) // ... 반영되는데 시간이 걸림
        onView(withId(R.id.rv_shopping_cart)).check(matchDescendantSoftly("2"))
    }

    @Test
    @DisplayName("상품이 1000원이고 수량이 1개일 때, 증가 시키면 총 가격이 2000가 된다")
    fun increase_count_price_test2() {
        // given
        startScenarioWith(fakeProduct())
        // when & then
        onView(withId(R.id.rv_shopping_cart)).performClickHolderAt<CartAdapter.CartViewHolder>(
            0,
            R.id.btn_increase_product,
        )
        Thread.sleep(100) // ... 반영되는데 시간이 걸림
        onView(withId(R.id.rv_shopping_cart)).check(matchDescendantSoftly("2,000"))
    }

    @Test
    @DisplayName("상품의 수량이 1개일 때, 감소 시키면 1개이다.")
    fun decrease_count_test() {
        // given
        startScenarioWith(fakeProduct())
        // when & then
        onView(withId(R.id.rv_shopping_cart)).performClickHolderAt<CartAdapter.CartViewHolder>(
            0,
            R.id.btn_decrease_product,
        )
        onView(withId(R.id.rv_shopping_cart)).check(matchDescendantSoftly("1"))
    }

    @Test
    @DisplayName("상품의 수량이 3개일 때, 감소 시키면 2개이다.")
    fun decrease_count_test2() {
        // given
        startScenarioWith(fakeProduct(), fakeProduct(), fakeProduct())
        // when & then
        onView(withId(R.id.rv_shopping_cart)).performClickHolderAt<CartAdapter.CartViewHolder>(
            0,
            R.id.btn_decrease_product,
        )
        Thread.sleep(100) // ... 반영되는데 시간이 걸림
        onView(withId(R.id.rv_shopping_cart)).check(matchDescendantSoftly("2"))
    }

    @Test
    @DisplayName("상품이 1000원이고 수량이 3개일 때, 감소 시키면 총 가격이 2000가 된다")
    fun decrease_count_price_test2() {
        // given
        startScenarioWith(fakeProduct())
        // when & then
        onView(withId(R.id.rv_shopping_cart)).performClickHolderAt<CartAdapter.CartViewHolder>(
            0,
            R.id.btn_increase_product,
        )
        Thread.sleep(100)
        onView(withId(R.id.rv_shopping_cart)).check(matchDescendantSoftly("2,000"))
    }

    @Test
    @DisplayName("현재 페이지가 1개이고 장바구니에 상품이 5개 있으면, 다음 페이지 버튼이 비활성화 되어 있다.")
    fun next_page_disabled_test() {
        // given
        val totalProductCount = 5
        startScenarioWith(fakeProducts(totalProductCount))
        // when & then
        onView(withId(R.id.tv_plus_page))
            .check(matches(isNotEnabled()))
    }

    @Test
    @DisplayName("현재 페이지가 1이고 장바구니에 상품이 6개 있으면, 다음 페이지 버튼이 활성화 되어 있다.")
    fun next_page_enabled_test() {
        // given
        val totalProductCount = 6
        startScenarioWith(fakeProducts(totalProductCount))
        // when & then
        onView(withId(R.id.tv_plus_page))
            .check(matches(isEnabled()))
    }

    @Test
    @DisplayName("현재 페이지가 1이고 장바구니에 상품이 6개 있을 때, 다음 페이지 버튼을 누르면, 1개의 상품이 보인다")
    fun move_next_page_test() {
        // given
        startScenarioWith(fakeProducts(6))
        // when
        onView(withId(R.id.tv_plus_page))
            .perform(ViewActions.click())
        // then
        val expectCount = 1
        val expectProductTitle = "6"
        onView(withId(R.id.rv_shopping_cart))
            .check(matchDescendantSoftly(expectProductTitle))
        onView(withId(R.id.rv_shopping_cart))
            .check(withItemCount(expectCount))
    }

    @Test
    @DisplayName(
        "현재 페이지가 1이고 장바구니에 상품이 총 6개 있을 때, 3번째 상품을 삭제 하면 " +
            "5 개의 상품이 현재 페이지에 보인다",
    )
    fun delete_test1() {
        // given
        val deletePosition = 2
        val totalProductCount = 6
        startScenarioWith(fakeProducts(totalProductCount))
        // when
        onView(withId(R.id.rv_shopping_cart)).performScrollToHolder<CartAdapter.CartViewHolder>(
            deletePosition,
        )
        onView(withId(R.id.rv_shopping_cart)).performClickHolderAt<CartAdapter.CartViewHolder>(
            deletePosition,
            R.id.iv_shooping_cart_delete,
        )
        // then
        val expectCount = 5
        onView(withId(R.id.rv_shopping_cart)).check(withItemCount(expectCount))
    }

    @Test
    @DisplayName(
        "현재 페이지가 1이고 장바구니에 상품이 총 6개 있을 때, 3번째 상품을 삭제 하면 " +
            "5번째 위치에 6 번째 상품이 현재 페이지에 보인다",
    )
    fun delete_test2() {
        // given
        val deletePosition = 2
        val addedPosition = 4
        val totalProductCount = 6
        startScenarioWith(fakeProducts(totalProductCount))
        // when
        onView(withId(R.id.rv_shopping_cart)).performScrollToHolder<CartAdapter.CartViewHolder>(
            deletePosition,
        )
        onView(withId(R.id.rv_shopping_cart)).performClickHolderAt<CartAdapter.CartViewHolder>(
            deletePosition,
            R.id.iv_shooping_cart_delete,
        )
        // then
        val expectProductTitle = "6"
        onView(withId(R.id.rv_shopping_cart)).performScrollToHolder<CartAdapter.CartViewHolder>(
            addedPosition,
        ).check(
            matchDescendantSoftly(
                expectProductTitle,
            ),
        )
    }

    @Test
    @DisplayName(
        "현재 페이지가 1이고 장바구니에 상품이 5개 있을 때, 3번째 상품을 삭제 하면 " +
            "4개가 보인다",
    )
    fun delete_test3() {
        // given
        val deletePosition = 2
        val totalProductCount = 5
        startScenarioWith(fakeProducts(totalProductCount))
        // when
        onView(withId(R.id.rv_shopping_cart)).performScrollToHolder<CartAdapter.CartViewHolder>(
            deletePosition,
        )
        onView(withId(R.id.rv_shopping_cart)).performClickHolderAt<CartAdapter.CartViewHolder>(
            deletePosition,
            R.id.iv_shooping_cart_delete,
        )
        // then
        val expectCount = 4
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
