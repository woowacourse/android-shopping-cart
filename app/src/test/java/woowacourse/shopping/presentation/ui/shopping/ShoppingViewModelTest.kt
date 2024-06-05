package woowacourse.shopping.presentation.ui.shopping

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.cartProducts
import woowacourse.shopping.domain.Repository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewModel.Companion.LOAD_ERROR

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ShoppingViewModelTest {
    @RelaxedMockK
    private lateinit var repository: Repository

    @InjectMockKs
    private lateinit var viewModel: ShoppingViewModel

    @Test
    fun `viewModel이 초기화되면 데이터가 20개 불러와진다`() {
        every { repository.findProductByPagingWithMock(any(), any()) } returns Result.success(cartProducts)
        viewModel.loadProductByOffset()
        Thread.sleep(1000)
        assertEquals(viewModel.products.getOrAwaitValue(), UiState.Success(cartProducts))
    }

    @Test
    fun `viewModel에서 데이터 로드가 실패하면 Error로 상태가 변화한다`() {
        every { repository.findProductByPagingWithMock(any(), any()) } returns Result.failure(Throwable())
        viewModel.loadProductByOffset()
        Thread.sleep(1000)
        assertEquals(viewModel.errorHandler.getOrAwaitValue(1).getContentIfNotHandled(), LOAD_ERROR)
    }
}
