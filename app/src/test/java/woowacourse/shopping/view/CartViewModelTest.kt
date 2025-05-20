package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.FakeCartStorage
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.view.cart.vm.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: CartViewModel
    private lateinit var cartStorage: FakeCartStorage

    @BeforeEach
    fun setUp() {
        cartStorage = FakeCartStorage()
        viewModel = CartViewModel(cartStorage)
    }

    @Test
    fun `첫페이지_불러오기`() {
        // When
        viewModel.loadCarts()

        // Then
        val products = viewModel.products.getOrAwaitValue()
        val page = viewModel.pageNumber.getOrAwaitValue()
        val state = viewModel.pageState.getOrAwaitValue()

        assertThat(products).hasSize(5)
        assertThat(page).isEqualTo(1)
        assertThat(state.nextPageEnabled).isTrue()
        assertThat(state.previousPageEnabled).isFalse()
    }

    @Test
    fun `페이지_추가_동작`() {
        // When
        viewModel.addPage()
        viewModel.addPage()

        // Then
        val page = viewModel.pageNumber.getOrAwaitValue()
        val products = viewModel.products.getOrAwaitValue()

        assertThat(page).isEqualTo(3)
        assertThat(products).hasSize(1)
    }

    @Test
    fun `페이지_이전_동작`() {
        // Given
        viewModel.addPage()
        viewModel.addPage()
        // When
        viewModel.subPage()

        // Then
        val page = viewModel.pageNumber.getOrAwaitValue()
        val products = viewModel.products.getOrAwaitValue()

        assertThat(page).isEqualTo(2)
        assertThat(products).hasSize(5)
    }

    @Test
    fun `상품_삭제시_페이지_개수_조정`() {
        // When
        viewModel.deleteProduct(1)
        viewModel.deleteProduct(2)

        // Then
        val products = viewModel.products.getOrAwaitValue()

        assertThat(products).hasSize(5)
    }
}
