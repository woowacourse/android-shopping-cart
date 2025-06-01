package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartSinglePage
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.view.cart.vm.CartViewModel
import woowacourse.shopping.view.loader.CartLoader
import woowacourse.shopping.view.main.state.ProductState

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel
    private lateinit var cartRepository: CartRepository

    private lateinit var cartLoader: CartLoader

    @BeforeEach
    fun setUp() {
        cartRepository = mockk()
        cartLoader = mockk()

        val dummyPages =
            listOf(
                (1L..5L).map { Cart(Quantity(1), it) },
                (6L..10L).map { Cart(Quantity(1), it) },
                (11L..11L).map { Cart(Quantity(1), it) },
            )

        every {
            cartRepository.singlePage(any(), any(), any())
        } answers {
            val page = firstArg<Int>()
            val callback = thirdArg<(CartSinglePage) -> Unit>()
            val hasNext = page + 1 < dummyPages.size
            callback(CartSinglePage(dummyPages.getOrElse(page) { emptyList() }, hasNext))
        }

        every { cartRepository.delete(any(), any()) } answers {
            val productId = firstArg<Long>()
            val callback = secondArg<(() -> Unit)>()
            dummyPages.forEach { page ->
                page.filter { it.productId != productId }
            }
            dummyPages.filter { it.isNotEmpty() }
            callback()
        }

        every {
            cartLoader.invoke(any(), any(), any())
        } answers {
            val pageIndex = firstArg<Int>()
            val callback = thirdArg<(List<ProductState>, Boolean) -> Unit>()
            val carts = dummyPages.getOrElse(pageIndex) { emptyList() }
            val result =
                carts.map {
                    ProductState(Product(it.productId, "치즈닭갈비덮밥", "", Price(1000), Quantity(1)), it.quantity)
                }
            val hasNext = pageIndex + 1 < dummyPages.size
            callback(result, hasNext)
        }

        viewModel = CartViewModel(cartRepository, cartLoader)
    }

    @Test
    fun `첫 페이지를 불러온다`() {
        viewModel.loadCarts()

        val state = viewModel.uiState.getOrAwaitValue()

        assertAll(
            { assertThat(state.items).hasSize(5) },
            { assertThat(state.items.map { it.item.id }).containsExactly(1L, 2L, 3L, 4L, 5L) },
            { assertThat(state.pageState.page).isEqualTo(1) },
            { assertThat(state.pageState.nextPageEnabled).isTrue() },
            { assertThat(state.pageState.previousPageEnabled).isFalse() },
        )
    }

    @Test
    fun `페이지를 두 번 추가하면 세 번째 페이지로 이동한다`() {
        viewModel.addPage()
        viewModel.addPage()

        val state = viewModel.uiState.getOrAwaitValue()

        assertAll(
            { assertThat(state.pageState.page).isEqualTo(3) },
            { assertThat(state.items).hasSize(1) },
            { assertThat(state.items.first().item.id).isEqualTo(11L) },
        )
    }

    @Test
    fun `두 페이지 이동 후 한 페이지 이전하면 두 번째 페이지로 이동한다`() {
        viewModel.addPage()
        viewModel.addPage()
        viewModel.subPage()

        val state = viewModel.uiState.getOrAwaitValue()

        assertAll(
            { assertThat(state.pageState.page).isEqualTo(2) },
            { assertThat(state.items).hasSize(5) },
            { assertThat(state.items.map { it.item.id }).containsExactly(6L, 7L, 8L, 9L, 10L) },
        )
    }
}
