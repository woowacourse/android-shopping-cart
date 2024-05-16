package woowacourse.shopping.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.data.product.ProductRepository

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private val cartRepository: CartRepository = CartDummyRepository
    private val productRepository: ProductRepository = ProductDummyRepository
    private val pageSize: Int = 5

    @BeforeEach
    fun setUp() {
        viewModel = CartViewModel(cartRepository, productRepository)
        cartRepository.deleteAll()
    }

    @Test
    fun `장바구니에 상품을 추가한다`() {
        // when
        viewModel.add(0)
        viewModel.loadCount()

        // then
        val actual = viewModel.cartSize.getOrAwaitValue()
        assertThat(actual).isEqualTo(1)
    }

    @Test
    fun `장바구니에 담긴 상품을 삭제한다`() {
        // given
        val product = productRepository.find(0L)
        viewModel.add(product.id)

        // when
        viewModel.delete(product)
        viewModel.loadCount()

        // then
        val actual = viewModel.cartSize.getOrAwaitValue()
        assertThat(actual).isEqualTo(0)
    }

    @Test
    fun `한 페이지에는 5개의 장바구니 상품이 있다`() {
        // given
        repeat(pageSize) {
            val product = productRepository.find(it.toLong())
            cartRepository.increaseQuantity(product)
        }

        // when
        viewModel.loadCart(0, pageSize)

        // then
        val actual = viewModel.cart.getOrAwaitValue()
        assertThat(actual).hasSize(pageSize)
        assertThat(actual).isEqualTo(cartRepository.findRange(0, pageSize))
    }

    @Test
    fun `장바구니 상품이 10개인 경우 5개의 상품을 불러온다`() {
        // given
        repeat(10) {
            val product = productRepository.find(it.toLong())
            cartRepository.increaseQuantity(product)
        }

        // when
        viewModel.loadCart(0, pageSize)

        // then
        val actual = viewModel.cart.getOrAwaitValue()
        assertThat(actual).hasSize(pageSize)
        assertThat(actual).isEqualTo(cartRepository.findRange(0, pageSize))
    }

    @Test
    fun `장바구니에 6개의 상품을 담았다면 장바구니 상품 수는 6이다`() {
        // given
        repeat(6) {
            val product = productRepository.find(it.toLong())
            cartRepository.increaseQuantity(product)
        }

        // when
        viewModel.loadCount()

        // then
        val actual = viewModel.cartSize.getOrAwaitValue()
        assertThat(actual).isEqualTo(6)
    }
}
