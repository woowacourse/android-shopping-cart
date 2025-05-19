package woowacourse.shopping.view.cart

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isNotClickable
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.R
import woowacourse.shopping.RepositoryProvider
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.fixture.dummyProductsFixture
import woowacourse.shopping.presentation.view.cart.CartFragment
import woowacourse.shopping.util.clickOnViewChild
import woowacourse.shopping.util.nthChildOf

class CartFragmentTest {
    private lateinit var fragmentScenario: FragmentScenario<CartFragment>

    private val fakeRepository =
        object : CartRepository {
            private val shoppingCart = dummyProductsFixture.take(15).toMutableList()

            override fun getCartItems(
                limit: Int,
                offset: Int,
                onResult: (Result<PageableItem<CartItem>>) -> Unit,
            ) {
                val result =
                    runCatching {
                        val products = shoppingCart.subList(offset, offset + limit)
                        val cartItems = products.map { CartItem(it.id, it, 1) }
                        val hasMore =
                            products
                                .lastOrNull()
                                ?.let { shoppingCart.any { product -> it.id < product.id } } ?: false
                        PageableItem(cartItems, hasMore)
                    }
                onResult(result)
            }

            override fun deleteCartItem(
                id: Long,
                onResult: (Result<Long>) -> Unit,
            ) {
                val result =
                    runCatching {
                        val foundItem = shoppingCart.find { it.id == id }
                        if (foundItem != null) {
                            shoppingCart.remove(foundItem)
                        }
                        id
                    }
                onResult(result)
            }

            override fun addCartItem(
                product: Product,
                onResult: (Result<Unit>) -> Unit,
            ) {
            }
        }

    @Before
    fun setup() {
        RepositoryProvider.initCartRepository(fakeRepository)
        fragmentScenario =
            launchFragmentInContainer(
                themeResId = R.style.Theme_Shopping,
            ) { CartFragment() }
    }

    @Test
    fun `장바구니_아이팀을_확인할_수_있다`() {
        firstProductInRecyclerView().check(matches(withText(dummyProductsFixture[0].name)))
    }

    @Test
    fun `다음_페이지_버튼을_누르면_페이지_수가_증가한다`() {
        onView(withId(R.id.btn_right)).perform(click())
        onView(withId(R.id.text_view_page)).check(matches(withText("2")))
    }

    @Test
    fun `이전_페이지_버튼을_누르면_페이지_수가_감소한다`() {
        onView(withId(R.id.btn_right)).perform(click())
        onView(withId(R.id.btn_right)).perform(click())

        onView(withId(R.id.btn_left)).perform(click())

        onView(withId(R.id.text_view_page)).check(matches(withText("2")))
    }

    @Test
    fun `처음_페이지에서는_이전페이지_버튼을_클릭할_수_없다`() {
        onView(withId(R.id.text_view_page)).check(matches(withText("1")))

        onView(withId(R.id.btn_left)).check(matches(isNotClickable()))
    }

    @Test
    fun `마지막_페이지에서는_다음페이지_버튼을_클릭할_수_없다`() {
        onView(withId(R.id.btn_right)).perform(click())
        onView(withId(R.id.btn_right)).perform(click())

        onView(withId(R.id.text_view_page)).check(matches(withText("3")))

        onView(withId(R.id.btn_right)).check(matches(isNotClickable()))
    }

    @Test
    fun `장바구니에_담긴_상품을_제거할_수_있다`() {
        firstProductInRecyclerView().check(matches(withText(dummyProductsFixture[0].name)))

        onView(withId(R.id.recycler_view_cart))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickOnViewChild(R.id.btn_remove_cart),
                ),
            )

        firstProductInRecyclerView().check(matches(withText(dummyProductsFixture[1].name)))
    }

    private fun firstProductInRecyclerView() =
        onView(
            allOf(
                withId(R.id.cart_item_name),
                isDescendantOfA(nthChildOf(withId(R.id.recycler_view_cart), 0)),
            ),
        )
}
