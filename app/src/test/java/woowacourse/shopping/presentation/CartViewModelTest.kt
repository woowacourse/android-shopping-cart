package woowacourse.shopping.presentation

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.cart.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var repository: ProductRepository
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        repository = mockk<ProductRepository>(relaxed = true)

        every { repository.getPagedCartProducts(any(), any(), any()) } just Runs

        every {
            repository.getCartProductCount(any())
        } answers {
            val callback = firstArg<(Result<Int>) -> Unit>()
            callback(Result.success(10))
        }

        viewModel = CartViewModel(repository)
    }

    @Test
    fun `다음_페이지_버튼을_누르면_페이지_값이_1_증가한다`() {
        viewModel.changePage(next = true)

        val currentPage = viewModel.currentPage.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(1)
    }

    @Test
    fun `이전_페이지_버튼을_누르면_페이지_값이_1_감소한다`() {
        viewModel.changePage(next = true)
        viewModel.changePage(next = false)

        val currentPage = viewModel.currentPage.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(0)
    }

    @Test
    fun `페이지가_0일_때_이전_페이지_버튼을_누르면_페이지_값이_0으로_유지된다`() {
        viewModel.changePage(next = false)

        val currentPage = viewModel.currentPage.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(0)
    }
}
