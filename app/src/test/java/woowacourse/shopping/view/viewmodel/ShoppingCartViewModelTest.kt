package woowacourse.shopping.view.viewmodel

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.MockShoppingCartRepository
import woowacourse.shopping.TestFixture.getOrAwaitValue
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.view.cart.ShoppingCartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var viewModel: ShoppingCartViewModel

    @BeforeEach
    fun setUp() {
        shoppingCartRepository = MockShoppingCartRepository()
        viewModel = ShoppingCartViewModel(shoppingCartRepository)
    }

    @Test
    fun `offset을_기준으로_장바구니_리스트를_요청하면_장바구니_정해진_개수만큼_반환해야_한다`() {
        val before = viewModel.shoppingCart.cartItems.getOrAwaitValue()
        Assertions.assertThat(before.size).isEqualTo(0)

        viewModel.loadPagingCartItemList(3)

        val result = viewModel.shoppingCart.cartItems.getOrAwaitValue()
        Assertions.assertThat(result.size).isEqualTo(3)
    }

    @Test
    fun `장바구니_id로_장바구니_목록을_삭제하면_전체_상품에서_해당_id와_일치하는_아이템이_삭제되어야_한다`() {
        viewModel.loadPagingCartItemList(3)
        val before = viewModel.shoppingCart.cartItems.getOrAwaitValue()
        Assertions.assertThat(before.size).isEqualTo(3)

        viewModel.deleteShoppingCartItem(0, 0)

        val result = viewModel.shoppingCart.cartItems.getOrAwaitValue()
        Assertions.assertThat(result.size).isEqualTo(2)
    }
}
