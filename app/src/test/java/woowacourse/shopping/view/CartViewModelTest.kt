package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.domain.CartResult
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.view.cart.vm.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: CartViewModel
    private lateinit var repository: CartRepository
    private val cartStorage: CartStorage = mockk()

    @BeforeEach
    fun setUp() {
        repository = CartRepositoryImpl(cartStorage)
        viewModel = CartViewModel(repository)

        every { cartStorage.singlePage(0, 5) } returns
            CartResult(
                products = (1L..5L).map { Product(it, "Product", Price(1000), "") },
                hasNextPage = true,
            )

        every { cartStorage.singlePage(5, 10) } returns
            CartResult(
                products = (6L..10L).map { Product(it, "Product", Price(2000), "") },
                hasNextPage = true,
            )

        every { cartStorage.singlePage(10, 15) } returns
            CartResult(
                products = listOf(Product(11L, "Product", Price(3000), "")),
                hasNextPage = false,
            )

        every { cartStorage.deleteProduct(any()) } just Runs
    }

    @Test
    fun `첫페이지_불러오기`() {
        // When
        viewModel.loadCarts()

        // Then
        val products = viewModel.products.getOrAwaitValue()
        val pageState = viewModel.pageState.getOrAwaitValue()
        val state = viewModel.pageState.getOrAwaitValue()

        assertAll(
            { assertThat(products).hasSize(5) },
            { assertThat(pageState.page).isEqualTo(1) },
            { assertThat(state.nextPageEnabled).isTrue() },
            { assertThat(state.previousPageEnabled).isFalse() },
        )
    }

    @Test
    fun `페이지_추가_동작`() {
        // When
        viewModel.addPage()
        viewModel.addPage()

        // Then
        val pageState = viewModel.pageState.getOrAwaitValue()
        val products = viewModel.products.getOrAwaitValue()

        assertThat(pageState.page).isEqualTo(3)
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
        val pageState = viewModel.pageState.getOrAwaitValue()
        val products = viewModel.products.getOrAwaitValue()

        assertThat(pageState.page).isEqualTo(2)
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
