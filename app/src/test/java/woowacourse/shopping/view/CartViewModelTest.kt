package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import net.bytebuddy.matcher.ElementMatchers.any
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
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.view.cart.vm.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel
    private lateinit var cartRepository: CartRepository
    private val productRepository: ProductRepository = mockk()

    @BeforeEach
    fun setUp() {
        cartRepository = mockk()
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
            // 비어있는 페이지 제거
            dummyPages.filter { it.isNotEmpty() }
            callback()
        }

        every {
            productRepository.getProduct(any(), any())
        } answers {
            val id = firstArg<Long>()
            val callback = secondArg<(Product) -> Unit>()
            callback(Product(id, "상품", "", Price(1000), Quantity(1)))
        }

        viewModel = CartViewModel(cartRepository, productRepository)
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

    @Test
    fun `상품 삭제 시 페이지 수가 줄어들 경우 마지막 페이지로 이동한다`() {
        // 마지막 페이지로 이동
        viewModel.addPage()
        viewModel.addPage()

        // 상품 두 개 삭제
        viewModel.deleteProduct(11)
        viewModel.deleteProduct(10)

        val state = viewModel.uiState.getOrAwaitValue()

        assertAll(
            { assertThat(state.pageState.page).isEqualTo(3) },
            { assertThat(state.items.map { it.item.id }).containsExactly(6L, 7L, 8L, 9L) },
        )
    }
}
