package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.fixture.productFixture2
import woowacourse.shopping.view.detail.vm.DetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var historyRepository: HistoryRepository

    @BeforeEach
    fun setup() {
        cartRepository = mockk(relaxed = true)
        productRepository = mockk()
        historyRepository = mockk(relaxed = true)

        every { productRepository.getProduct(any(), any()) } answers {
            val callback = secondArg<(Product) -> Unit>()
            callback(productFixture2)
        }

        viewModel = DetailViewModel(productRepository, cartRepository, historyRepository)
        viewModel.load(2L, 1L)
    }

    @Test
    fun `조회한_프로덕트를_로드한다`() {
        // when
        viewModel.load(2L, 2L)

        // then
        val expected = viewModel.uiState.getOrAwaitValue()

        assertThat(expected.product.item).isEqualTo(productFixture2)
    }

    @Test
    fun `장바구니_수량을_증가시킨다`() {
        viewModel.increaseCartQuantity()

        val state = viewModel.uiState.getOrAwaitValue()
        assertThat(state.product.cartQuantity.value).isEqualTo(2)
    }

    @Test
    fun `장바구니 수량을 감소시킨다`() {
        viewModel.increaseCartQuantity()
        viewModel.decreaseCartQuantity()

        val state = viewModel.uiState.getOrAwaitValue()
        assertThat(state.product.cartQuantity.value).isEqualTo(1)
    }

    @Test
    fun `장바구니_수량을_0_이하로_내릴_수_없다`() {
        viewModel.decreaseCartQuantity()

        val state = viewModel.uiState.getOrAwaitValue()
        assertThat(state.product.cartQuantity.value).isEqualTo(1)
    }

    @Test
    fun `상품이_이미_장바구니에_있다면_수량을_수정한다`() {
        every { cartRepository.getCart(any(), any()) } answers {
            val callback = secondArg<(Cart?) -> Unit>()
            callback(Cart(Quantity(2), 1L))
        }

        viewModel.saveCart(1L)

        verify {
            cartRepository.upsert(productFixture1.id, Quantity(1))
        }
    }
}
