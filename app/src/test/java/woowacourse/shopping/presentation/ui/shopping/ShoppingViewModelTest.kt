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
        viewModel.loadInitialProductByPage()
        assertEquals(viewModel.products.getOrAwaitValue(1), UiState.Success(products))
    }

    @Test
    fun `viewModel에서 데이터 로드가 실패하면 Error로 상태가 변화한다`() {
        every { repository.load(any(), any()) } returns Result.failure(Throwable())
        viewModel.loadInitialProductByPage()
        assertEquals(viewModel.error.getOrAwaitValue(1), true)
    }

    @Test
    fun `초기화 후, 더보기 버튼이 눌리면 데이터가 20개 더 불러와진다`() {
        every { repository.load(any(), any()) } returns Result.success(products)
        viewModel.loadInitialProductByPage()
        viewModel.addProductByPage()
        assertEquals(viewModel.products.getOrAwaitValue(1), UiState.Success(products + products))
    }

    @Test
    fun `더보기 버튼이 눌렸을 때 데이터 로드가 실패하면 Error로 상태가 변화한다`() {
        every { repository.load(any(), any()) } returns Result.failure(Throwable())
        viewModel.addProductByPage()
        assertEquals(viewModel.error.getOrAwaitValue(1), true)
    }
}
