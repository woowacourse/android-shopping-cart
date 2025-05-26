package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.FakeCartRepository
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.view.cart.vm.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel
    private lateinit var cartRepository: CartRepository

    @BeforeEach
    fun setUp() {
        cartRepository = FakeCartRepository(
        )
        viewModel = CartViewModel(cartRepository)
    }

    @Test
    fun `loadCarts는 현재 페이지에 해당하는 상품과 상태를 LiveData에 반영한다`() {
        // When
        viewModel.loadCarts()

        // Then
        val products = viewModel.carts.getOrAwaitValue()
        val pageNumber = viewModel.pageNumber.getOrAwaitValue()
        val pageState = viewModel.pageState.getOrAwaitValue()

        assertThat(products).hasSize(5)
        assertThat(pageNumber).isEqualTo(1)
        assertThat(pageState.previousPageEnabled).isFalse()
        assertThat(pageState.nextPageEnabled).isTrue()
        assertThat(pageState.pageVisibility).isTrue()
    }

    @Test
    fun `addPage는 다음 페이지로 이동하고 상품을 갱신한다`() {
        // When
        viewModel.addPage()

        // Then
        val products = viewModel.carts.getOrAwaitValue()
        val pageNumber = viewModel.pageNumber.getOrAwaitValue()

        assertThat(pageNumber).isEqualTo(2)
        assertThat(products).hasSize(1)
    }

    @Test
    fun `subPage는 이전 페이지로 이동하고 상품을 갱신한다`() {
        // Given
        viewModel.addPage()

        // When
        viewModel.subPage()

        // Then
        val products = viewModel.carts.getOrAwaitValue()
        val pageNumber = viewModel.pageNumber.getOrAwaitValue()

        assertThat(pageNumber).isEqualTo(1)
        assertThat(products).hasSize(5)
    }

    @Test
    fun `deleteProduct는 상품을 삭제하고 페이지가 비었으면 이전 페이지로 이동한다`() {
        // Given
        viewModel.addPage()
        assertThat(viewModel.pageNumber.getOrAwaitValue()).isEqualTo(2)

        viewModel.deleteProduct(6L)

        val pageNumber = viewModel.pageNumber.getOrAwaitValue()
        val products = viewModel.carts.getOrAwaitValue()

        assertThat(pageNumber).isEqualTo(1)
        assertThat(products).hasSize(5)
    }

    @Test
    fun `deleteProduct는 상품을 삭제하되 페이지가 비지 않으면 이동하지 않는다`() {
        // When
        viewModel.deleteProduct(1L)

        // Then
        val pageNumber = viewModel.pageNumber.getOrAwaitValue()
        val products = viewModel.carts.getOrAwaitValue()

        assertThat(pageNumber).isEqualTo(1)
        assertThat(products).hasSize(5)
    }
}
