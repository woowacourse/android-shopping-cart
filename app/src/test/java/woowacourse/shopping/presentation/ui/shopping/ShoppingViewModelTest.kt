package woowacourse.shopping.presentation.ui.shopping

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.remote.DummyProductRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.shopping.ShoppingViewModel.Companion.LOAD_ERROR
import woowacourse.shopping.products

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ShoppingViewModelTest {
    @MockK
    private lateinit var repository: DummyProductRepository

    @InjectMockKs
    private lateinit var viewModel: ShoppingViewModel

    @Test
    fun `viewModel이 초기화되면 데이터가 20개 불러와진다`() {
        every { repository.load(any(), any()) } returns Result.success(products)
        viewModel.loadProductByOffset()
        assertEquals(viewModel.products.getOrAwaitValue(1), UiState.Finish(products))
    }

    @Test
    fun `viewModel에서 데이터 로드가 실패하면 Error로 상태가 변화한다`() {
        every { repository.load(any(), any()) } returns Result.failure(Throwable())
        viewModel.loadProductByOffset()
        assertEquals(viewModel.products.getOrAwaitValue(1), UiState.Error(LOAD_ERROR))
    }
}
