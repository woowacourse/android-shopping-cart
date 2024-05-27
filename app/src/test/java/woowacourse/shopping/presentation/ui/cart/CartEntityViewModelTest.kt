package woowacourse.shopping.presentation.ui.cart

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.cartProduct
import woowacourse.shopping.cartProducts
import woowacourse.shopping.domain.Repository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.cart.CartViewModel.Companion.CART_DELETE_ERROR
import woowacourse.shopping.presentation.ui.cart.CartViewModel.Companion.CART_LOAD_ERROR

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class CartEntityViewModelTest {
    @MockK
    private lateinit var productCartRepository: Repository

    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        every { productCartRepository.getMaxCartCount() } returns Result.success(0) // 초기 오프셋 처리
        viewModel = CartViewModel(productCartRepository)
    }

    @Test
    fun `카트 아이템을 pageCount개씩 불러온다`() {
        every { productCartRepository.findCartByPaging(any(), any()) } returns
            Result.success(
                cartProducts,
            )
        viewModel.findProductByOffset()
        Thread.sleep(1000)
        assertThat(viewModel.carts.getOrAwaitValue()).isEqualTo(UiState.Success(cartProducts))
    }

    @Test
    fun `카트 아이템을 불러오기 실패하면 Error 상태로 변화한다`() {
        every { productCartRepository.findCartByPaging(any(), any()) } returns Result.failure(Throwable())
        viewModel.findProductByOffset()
        Thread.sleep(1000)
        assertThat(viewModel.errorHandler.getOrAwaitValue().getContentIfNotHandled()).isEqualTo(CART_LOAD_ERROR)
    }

    @Test
    fun `데이터를 삭제한 뒤에 새로운 데이터를 불러온다`() {
        every { productCartRepository.deleteCart(any()) } returns Result.success(0)
        every { productCartRepository.getMaxCartCount() } returns Result.success(0)
        every { productCartRepository.findCartByPaging(any(), any()) } returns
            Result.success(
                cartProducts,
            )
        viewModel.onDelete(cartProduct)
        Thread.sleep(1000)
        assertThat(viewModel.carts.getOrAwaitValue()).isEqualTo(UiState.Success(cartProducts))
    }

    @Test
    fun `데이터 삭제에 실패하면 Error 상태로 변화한다`() {
        every { productCartRepository.deleteCart(any()) } returns Result.failure(Throwable())
        viewModel.onDelete(cartProduct)
        Thread.sleep(1000)
        assertThat(viewModel.errorHandler.getOrAwaitValue().getContentIfNotHandled()).isEqualTo(CART_DELETE_ERROR)
    }
}
