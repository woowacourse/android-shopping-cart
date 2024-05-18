package woowacourse.shopping.presentation.ui.cart

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.dummyCarts
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.cart.CartViewModel.Companion.CART_DELETE_ERROR
import woowacourse.shopping.presentation.ui.cart.CartViewModel.Companion.CART_LOAD_ERROR
import woowacourse.shopping.product

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class CartViewModelTest {
    @MockK
    private lateinit var cartRepository: CartRepository

    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        every { cartRepository.getMaxPage(any()) } returns Result.success(0) // 초기 오프셋 처리
        viewModel = CartViewModel(cartRepository)
    }

    @Test
    fun `카트 아이템을 pageCount개씩 불러온다`() {
        every { cartRepository.load(any(), any()) } returns Result.success(dummyCarts)
        viewModel.loadProductByPage()
        assertThat(viewModel.carts.getOrAwaitValue(3)).isEqualTo(UiState.Success(dummyCarts))
    }

    @Test
    fun `카트 아이템을 불러오기 실패하면 Error 상태로 변화한다`() {
        every { cartRepository.load(any(), any()) } returns Result.failure(Throwable())
        viewModel.loadProductByPage()
        assertThat(viewModel.carts.getOrAwaitValue(3)).isEqualTo(UiState.Error(CART_LOAD_ERROR))
    }

    @Test
    fun `데이터를 삭제한 뒤에 새로운 데이터를 불러온다`() {
        every { cartRepository.delete(any()) } returns Result.success(0)
        every { cartRepository.getMaxPage(any()) } returns Result.success(0)
        every { cartRepository.load(any(), any()) } returns Result.success(dummyCarts)
        viewModel.deleteProduct(product)
        assertThat(viewModel.carts.getOrAwaitValue(3)).isEqualTo(UiState.Success(dummyCarts))
    }

    @Test
    fun `데이터 삭제에 실패하면 Error 상태로 변화한다`() {
        every { cartRepository.delete(any()) } returns Result.failure(Throwable())
        viewModel.deleteProduct(product)
        assertThat(viewModel.carts.getOrAwaitValue(3)).isEqualTo(UiState.Error(CART_DELETE_ERROR))
    }
}
