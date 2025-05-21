package woowacourse.shopping.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.ProductRepositoryImpl
import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.data.storage.ProductStorage
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
    private val productStorage: ProductStorage = mockk()
    private val cartStorage: CartStorage = mockk()

    @BeforeEach
    fun setup() {
        every { productStorage[1L] } returns productFixture1
        every { cartStorage[1L] } returns Cart(productId = 1L, quantity = Quantity(0))
        every { cartStorage.insert(any()) } just Runs

        cartRepository = CartRepositoryImpl(cartStorage)
        productRepository = ProductRepositoryImpl(productStorage)
        viewModel = DetailViewModel(productRepository, cartRepository)
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
    fun `장바구니_상품_저장소에_현재_상품을_추가한다`() {
        // given
        every { cartStorage[1L] } returns null
        every { cartStorage.insert(any()) } just Runs
        every { productStorage[1L] } returns productFixture1
        viewModel = DetailViewModel(productRepository, cartRepository)

        viewModel.load(1L)

        // when
        viewModel.saveCart()

        // then
        verify {
            cartStorage.insert(
                match {
                    it.productId == 1L && it.quantity == Quantity(1)
                },
            )
        }
    }

    @Test
    fun `이미_장바구니에_상품이_있는_경우_수량을_수정한다`() {
        // given
        every { cartStorage[1L] } returns Cart(productId = 1L, quantity = Quantity(2))
        every { cartStorage.modifyQuantity(any(), any()) } just Runs
        every { productStorage[1L] } returns productFixture1
        viewModel = DetailViewModel(productRepository, cartRepository)

        viewModel.load(1L)

        // when
        viewModel.saveCart()

        // then
        verify { cartStorage.modifyQuantity(1L, Quantity(1)) }
    }
}
