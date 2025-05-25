package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductSinglePage
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.fixture.productFixture2
import woowacourse.shopping.fixture.productFixture3
import woowacourse.shopping.fixture.productFixture4
import woowacourse.shopping.view.loader.HistoryLoader
import woowacourse.shopping.view.loader.ProductWithCartLoader
import woowacourse.shopping.view.main.MainUiEvent
import woowacourse.shopping.view.main.state.LoadState
import woowacourse.shopping.view.main.vm.MainViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository
    private lateinit var historyRepository: HistoryRepository

    private lateinit var productWithCartLoader: ProductWithCartLoader
    private lateinit var historyLoader: HistoryLoader

    private val products =
        listOf(
            productFixture1,
            productFixture2,
            productFixture3,
            productFixture4,
        )

    @BeforeEach
    fun setUp() {
        historyRepository = mockk()
        productRepository = mockk()
        cartRepository = mockk()

        every { productRepository.loadSinglePage(any(), any(), any()) } answers {
            val callback = thirdArg<(ProductSinglePage) -> Unit>()
            callback(ProductSinglePage(products, true))
        }

        every { cartRepository.getCarts(any(), any()) } answers {
            val callback = secondArg<(List<Cart?>) -> Unit>()
            val carts = products.map { Cart(Quantity(1), it.id) }
            callback(carts)
        }

        every { historyRepository.getHistory(any()) } answers {
            val callback = firstArg<(List<Long>) -> Unit>()
            callback(products.map { it.id })
        }

        every { productRepository.getProducts(any(), any()) } answers {
            val ids = firstArg<List<Long>>()
            val callback = secondArg<(List<Product>) -> Unit>()
            val filtered = products.filter { it.id in ids }
            callback(filtered)
        }

        every { cartRepository.upsert(any(), any()) } just Runs
        every { cartRepository.delete(any()) } just Runs

        productWithCartLoader = ProductWithCartLoader(productRepository, cartRepository)
        historyLoader = HistoryLoader(productRepository, historyRepository)

        viewModel = MainViewModel(cartRepository, historyRepository, productWithCartLoader, historyLoader)
    }

    @Test
    fun `저장소의 상품 목록을 가져온다`() {
        val result = viewModel.uiState.getOrAwaitValue()

        assertAll(
            { assertThat(result.productItems).hasSize(4) },
            { assertThat(result.historyItems).hasSize(4) },
            { assertThat(result.load).isInstanceOf(LoadState.CanLoad::class.java) },
            { assertThat(result.productItems.map { it.item.id }).containsExactly(1L, 2L, 3L, 4L) },
            { assertThat(result.historyItems.map { it.productId }).containsExactly(1L, 2L, 3L, 4L) },
        )
    }

    @Test
    fun `loadPage를 호출하면 다음 페이지의 상품이 추가되고 상태가 갱신된다`() {
        // given
        val initialItems = viewModel.uiState.getOrAwaitValue().productItems

        // when
        viewModel.loadPage()

        // then
        val updatedItems = viewModel.uiState.getOrAwaitValue().productItems
        assertThat(updatedItems).hasSize(8)
    }

    @Test
    fun `syncCartQuantities를 호출하면 장바구니 수량이 동기화된다`() {
        // given
        viewModel.uiState.getOrAwaitValue().productItems.map {
            it.copy(cartQuantity = Quantity(0))
        }

        // when
        viewModel.syncCartQuantities()

        // then
        val updated = viewModel.uiState.getOrAwaitValue().productItems
        assertThat(updated.map { it.cartQuantity.value }).allMatch { it == 1 }
    }

    @Test
    fun `decreaseCartQuantity 호출 시 수량이 감소한다`() {
        // given
        val product = viewModel.uiState.getOrAwaitValue().productItems.first()

        // when
        viewModel.decreaseCartQuantity(product.item.id)

        // then
        val updated = viewModel.uiState.getOrAwaitValue().productItems.first()
        Assertions.assertEquals(updated.cartQuantity.value, product.cartQuantity.value - 1)
    }

    @Test
    fun `increaseCartQuantity에서 수량 증가가 불가능한 경우 이벤트를 발생시킨다`() {
        // given
        val currentState = viewModel.uiState.getOrAwaitValue()
        val maxQuantity = currentState.productItems.first().copy(cartQuantity = Quantity(999))

        // when
        viewModel.increaseCartQuantity(maxQuantity.item.id)

        val event = viewModel.uiEvent.getValue()
        assertThat(event).isInstanceOf(MainUiEvent.ShowCannotIncrease::class.java)
    }
}
