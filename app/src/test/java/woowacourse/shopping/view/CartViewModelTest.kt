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
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartResult
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.view.cart.vm.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel
    private lateinit var repository: CartRepository
    private val cartStorage: CartStorage = mockk()
    private val productRepository: ProductRepository = mockk()

    @BeforeEach
    fun setUp() {
        repository = CartRepositoryImpl(cartStorage)
        viewModel = CartViewModel(repository, productRepository)

        every { cartStorage.singlePage(0, 5) } returns
            CartResult(
                carts = (1L..5L).map { Cart(productId = it) },
                hasNextPage = true,
            )
        every { cartStorage.singlePage(5, 10) } returns
            CartResult(
                carts = (6L..10L).map { Cart(productId = it) },
                hasNextPage = true,
            )
        every { cartStorage.singlePage(10, 15) } returns
            CartResult(
                carts = listOf(Cart(productId = 11L)),
                hasNextPage = false,
            )

        (1L..11L).forEach {
            every { productRepository[it] } returns Product(it, "Product$it", Price(1000), "")
        }

        every { cartStorage.delete(any()) } just Runs
    }

    @Test
    fun `첫페이지_불러오기`() {
        // When
        viewModel.loadCarts()

        // Then
        val items = viewModel.uiState.getOrAwaitValue()
        val carts = items.carts
        val pageState = items.pageState

        assertAll(
            { assertThat(carts).hasSize(5) },
            { assertThat(carts.map { it.product.id }).containsExactly(1L, 2L, 3L, 4L, 5L) },
            { assertThat(pageState.page).isEqualTo(1) },
            { assertThat(pageState.nextPageEnabled).isTrue() },
            { assertThat(pageState.previousPageEnabled).isFalse() },
        )
    }

    @Test
    fun `페이지_추가_동작`() {
        // When
        viewModel.addPage()
        viewModel.addPage()

        // Then
        val products = viewModel.uiState.getOrAwaitValue().carts
        val pageState = viewModel.uiState.getOrAwaitValue().pageState

        assertAll(
            { assertThat(pageState.page).isEqualTo(3) },
            { assertThat(products).hasSize(1) },
            { assertThat(products.first().product.id).isEqualTo(11L) },
        )
    }

    @Test
    fun `페이지_이전_동작`() {
        // Given
        viewModel.addPage()
        viewModel.addPage()

        // When
        viewModel.subPage()

        // Then
        val carts = viewModel.uiState.getOrAwaitValue().carts
        val pageState = viewModel.uiState.getOrAwaitValue().pageState

        assertAll(
            { assertThat(pageState.page).isEqualTo(2) },
            { assertThat(carts).hasSize(5) },
            { assertThat(carts.map { it.product.id }).containsExactly(6L, 7L, 8L, 9L, 10L) },
        )
    }

    @Test
    fun `상품_삭제시_페이지_개수_조정`() {
        // When
        viewModel.deleteProduct(1)
        viewModel.deleteProduct(2)

        // Then
        val products = viewModel.uiState.getOrAwaitValue()
        assertThat(products.carts).hasSize(5)
    }
}
