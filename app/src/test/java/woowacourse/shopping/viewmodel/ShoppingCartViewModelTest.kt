package woowacourse.shopping.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.DummyShoppingRepository
import woowacourse.shopping.UserShoppingCartRepository
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.shoppingcart.ShoppingCartViewModel
import woowacourse.shopping.uimodel.toCartItemUiModel
import woowacourse.shopping.viewmodel.fixtures.InstantTaskExecutorExtension
import woowacourse.shopping.viewmodel.fixtures.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var viewModel: ShoppingCartViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ShoppingCartViewModel(UserShoppingCartRepository)

        val cartItem = ShoppingCartItem(DummyShoppingRepository.productById(STUB_PRODUCT_ID))
        val shoppingCart = UserShoppingCartRepository.shoppingCart(0)
        UserShoppingCartRepository.updateShoppingCart(shoppingCart.addItem(cartItem))
    }

    @AfterEach
    fun tearDown() {
        UserShoppingCartRepository.deleteShoppingCartItem(STUB_PRODUCT_ID)
    }

    @Test
    fun `1페이지에 해당하는 데이터들을 로드한다`() {
        // when
        viewModel.loadCartItems(CURRENT_PAGE)

        // given
        val actual = viewModel.cartItemUiModels.getOrAwaitValue()

        // then
        assertThat(actual).containsExactly(
            UserShoppingCartRepository.shoppingCart(0).items[0].toCartItemUiModel())
    }

    @Test
    fun `데이터를 삭제하면, 카트 아이템의 개수가 1개 줄어든다`() {
        // when
        viewModel.loadCartItems(CURRENT_PAGE)
        val previous = viewModel.cartItemUiModels.getOrAwaitValue()
        assertThat(previous.size).isEqualTo(1)

        // given
        viewModel.deleteCartItem(0)

        // then
        val actual = viewModel.cartItemUiModels.getOrAwaitValue()
        assertThat(actual).hasSize(0)
    }

    companion object {
        private const val CURRENT_PAGE = 1
        private const val STUB_PRODUCT_ID = 0L
    }
}
