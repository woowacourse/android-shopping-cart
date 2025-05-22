package woowacourse.shopping.view

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.view.cart.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var repository: ProductRepository
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        repository = mockk()
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
}
