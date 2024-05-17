package woowacourse.shopping.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.productTestFixture
import woowacourse.shopping.repository.FakeShoppingCartItemRepository

class ShoppingCartViewModelTest {
    private lateinit var viewModel: ShoppingCartViewModel

    @Test
    fun `장바구니에 담긴 상품이 없을 때`() {
        // given
        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(mutableListOf()))

        // when
        val cartItems = viewModel.itemsInCurrentPage.value

        // then
        assertThat(cartItems).isEmpty()
    }

    @Test
    fun `장바구니에 담긴 상품이 총 3개일 때 첫 페이지 장바구니 개수는 3 `() {
        // given
        val fakeProducts =
            mutableListOf(
                productTestFixture(id = 1),
                productTestFixture(id = 2),
                productTestFixture(id = 3),
            )

        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))

        // when
        val cartItems = viewModel.itemsInCurrentPage.value

        // then
        assertThat(cartItems).hasSize(3)
    }

    @Test
    fun `장바구니에 담긴 상품이 총 8개 일 때 (5개 이상일 때) 첫 페이지 장바구니 개수는 5`() {
        // given
        val fakeProducts =
            mutableListOf(
                productTestFixture(id = 1),
                productTestFixture(id = 2),
                productTestFixture(id = 3),
                productTestFixture(id = 4),
                productTestFixture(id = 5),
                productTestFixture(id = 6),
                productTestFixture(id = 7),
                productTestFixture(id = 8),
            )
        viewModel = ShoppingCartViewModel(FakeShoppingCartItemRepository(fakeProducts))

        // when
        val cartItems = viewModel.itemsInCurrentPage.value

        // then
        assertThat(cartItems).hasSize(5)
    }
}
