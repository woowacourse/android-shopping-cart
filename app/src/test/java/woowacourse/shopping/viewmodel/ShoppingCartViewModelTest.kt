package woowacourse.shopping.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.DummyProductRepository
import woowacourse.shopping.DummyShoppingRepository
import woowacourse.shopping.domain.ShoppingCartItem
import woowacourse.shopping.productlist.toProductUiModel
import woowacourse.shopping.shoppingcart.LoadCartItemState
import woowacourse.shopping.shoppingcart.ShoppingCartViewModel
import woowacourse.shopping.viewmodel.fixtures.InstantTaskExecutorExtension
import woowacourse.shopping.viewmodel.fixtures.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var viewModel: ShoppingCartViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ShoppingCartViewModel(DummyShoppingRepository)

        val cartItem = ShoppingCartItem(DummyProductRepository.productById(STUB_PRODUCT_ID))
        val shoppingCart = DummyShoppingRepository.shoppingCart()
        DummyShoppingRepository.updateShoppingCart(shoppingCart.addItem(cartItem))
    }

    @AfterEach
    fun tearDown() {
        if (DummyShoppingRepository.shoppingCartSize() != 0) {
            DummyShoppingRepository.deleteShoppingCartItem(STUB_PRODUCT_ID)
        }
    }

    @Test
    fun `nextPage와 previousPage 메서드를 호출하기 전에는, 현재 페이지는 1이다`() {
        val currentPage = viewModel.currentPage.getOrAwaitValue()

        assertThat(currentPage).isEqualTo(CURRENT_PAGE)
    }

    @Test
    fun `1페이지에 해당하는 데이터들을 로드한다`() {
        // when
        viewModel.loadCartItems()

        // given
        val actual = viewModel.loadState.getOrAwaitValue() as LoadCartItemState.InitView

        // then
        assertThat(actual.result).containsExactly(DummyProductRepository.productById(0).toProductUiModel())
    }

    @Test
    fun `데이터를 삭제하면, 카트 아이템의 개수가 1개 줄어든다`() {
        // when
        viewModel.loadCartItems()
        val previous = viewModel.loadState.getOrAwaitValue() as LoadCartItemState.InitView
        assertThat(previous.result.size).isEqualTo(1)

        // given
        viewModel.deleteCartItem(STUB_PRODUCT_ID)

        // then
        viewModel.loadCartItems()
        val actual = viewModel.loadState.getOrAwaitValue() as LoadCartItemState.InitView
        assertThat(actual.result).hasSize(0)
    }

    companion object {
        private const val CURRENT_PAGE = 1
        private const val STUB_PRODUCT_ID = 0L
    }
}
