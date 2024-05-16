package woowacourse.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.fixtures.InstantTaskExecutorExtension
import woowacourse.shopping.fixtures.getOrAwaitValue
import woowacourse.shopping.productlist.toProductUiModel
import woowacourse.shopping.shoppingcart.ShoppingCartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var viewModel: ShoppingCartViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ShoppingCartViewModel(DummyShoppingRepository)

        val cartItem = ShoppingCartItem(DummyShoppingRepository.productById(STUB_PRODUCT_ID))
        val shoppingCart = DummyShoppingRepository.shoppingCart(0)
        DummyShoppingRepository.updateShoppingCart(shoppingCart.addItem(cartItem))
    }

    @AfterEach
    fun tearDown() {
        DummyShoppingRepository.deleteShoppingCartItem(STUB_PRODUCT_ID)
    }

    @Test
    fun `1페이지에 해당하는 데이터들을 로드한다`() {
        // when
        viewModel.loadCartItems(CURRENT_PAGE)

        // given
        val actual = viewModel.cartItems.getOrAwaitValue()

        // then
        assertThat(actual).containsExactly(DummyShoppingRepository.productById(0).toProductUiModel())
    }

    @Test
    fun `데이터를 삭제하면, 카트 아이템의 개수가 1개 줄어든다`() {
        // when
        viewModel.loadCartItems(CURRENT_PAGE)
        val previous = viewModel.cartItems.getOrAwaitValue()
        assertThat(previous.size).isEqualTo(1)

        // given
        viewModel.deleteCartItem(0)

        // then
        val actual = viewModel.cartItems.getOrAwaitValue()
        assertThat(actual).hasSize(0)
    }

    companion object {
        private const val CURRENT_PAGE = 1
        private const val STUB_PRODUCT_ID = 0L
    }
}
