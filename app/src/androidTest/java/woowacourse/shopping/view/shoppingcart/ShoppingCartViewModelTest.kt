@file:Suppress("ktlint")

package woowacourse.shopping.view.shoppingcart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.DummyShoppingCartRepositoryOld
import woowacourse.shopping.data.ShoppingCartRepositoryOld
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.shoppingCartItem
import woowacourse.shopping.shoppingCartLastPageItem

class ShoppingCartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: ShoppingCartRepositoryOld
    private lateinit var viewModel: ShoppingCartViewModel

    @Before
    fun setUp() {
        repository = DummyShoppingCartRepositoryOld()
        viewModel = ShoppingCartViewModel(repository)
    }

    @Test
    fun 한_페이지에_장바구니_상품이_5개씩_로드된다() {
        // when
        viewModel.requestPage(0)

        // then
        val actual = viewModel.cartItems.getOrAwaitValue()
        val expected = repository.getPage(5, 0)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun 장바구니에서_상품을_삭제할_수_있다() {
        // when
        viewModel.removeCartItem(shoppingCartItem)

        // then
        val result = viewModel.cartItems.getOrAwaitValue().items
        assertThat(result).doesNotContain(shoppingCartItem)
    }

    @Test
    fun 장바구니에서_상품을_삭제하면_해당_상품이_있었던_페이지가_로드된다() {
        // when
        viewModel.removeCartItem(shoppingCartLastPageItem)

        // then
        val actual = viewModel.cartItems.getOrAwaitValue()
        val expected = repository.getPage(5, 4)
        assertThat(actual).isEqualTo(expected)
    }
}
