package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.ext.getOrAwaitValue
import woowacourse.shopping.fixture.productFixture1
import woowacourse.shopping.view.detail.vm.DetailViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class DetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailViewModel
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductRepository

    @BeforeEach
    fun setup() {
        cartRepository = mockk(relaxed = true)
        productRepository = mockk()

        every { productRepository[1L] } returns productFixture1
        viewModel = DetailViewModel(productRepository, cartRepository)
        viewModel.load(1L)
    }

    @Test
    fun `조회한_프로덕트를_로드한다`() {
        // when
        viewModel.load(1L)

        // then
        val expected = viewModel.uiState.getOrAwaitValue()

        assertThat(expected.item).isEqualTo(productFixture1)
    }

    @Test
    fun `장바구니_수량을_증가시킨다`() {
        viewModel.increaseCartQuantity()

        val state = viewModel.uiState.getOrAwaitValue()
        assertThat(state.cartQuantity.value).isEqualTo(2)
    }

    @Test
    fun `장바구니 수량을 감소시킨다`() {
        viewModel.increaseCartQuantity()
        viewModel.decreaseCartQuantity()

        val state = viewModel.uiState.getOrAwaitValue()
        assertThat(state.cartQuantity.value).isEqualTo(1)
    }

    @Test
    fun `장바구니_수량을_0_이하로_내릴_수_없다`() {
        viewModel.decreaseCartQuantity()

        val state = viewModel.uiState.getOrAwaitValue()
        assertThat(state.cartQuantity.value).isEqualTo(1)
    }

    @Test
    fun `상품이_이미_장바구니에_있다면_수량을_수정한다`() {
        every { cartRepository.getCart(any(), any()) } answers {
            val callback = secondArg<(Cart?) -> Unit>()
            callback(Cart(Quantity(2), 1L))
        }

        viewModel.saveCart()

        verify {
            cartRepository.upsert(productFixture1.id, Quantity(1))
        }
    }

    @Test
    fun `상품이_장바구니에_없다면_추가한다`() {
        every { cartRepository.getCarts(any(), any()) } answers {
            val callback = secondArg<(Cart?) -> Unit>()
            callback(null)
        }

        viewModel.saveCart()

        verify {
            cartRepository.upsert(productFixture1.id, Quantity(1))
        }
    }
}
