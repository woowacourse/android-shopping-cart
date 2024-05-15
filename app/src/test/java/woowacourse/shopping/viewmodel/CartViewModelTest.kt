package woowacourse.shopping.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.cart.CartRepositoryImpl

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private lateinit var cartRepository: CartRepositoryImpl
    private val pageSize: Int = 5

    @BeforeEach
    fun setUp() {
        viewModel = CartViewModel()
        cartRepository = CartRepositoryImpl
        cartRepository.deleteAll()
    }

    @Test
    fun `장바구니에 상품을 추가한다`() {
        // when
        viewModel.add(cartRepository, 0)
        viewModel.loadCount(cartRepository)

        // then
        val actual = viewModel.cartSize.getOrAwaitValue()
        assertThat(actual).isEqualTo(1)
    }

    @Test
    fun `장바구니에 담긴 상품을 삭제한다`() {
        // given
        viewModel.add(cartRepository, 0)

        // when
        viewModel.delete(cartRepository, 0)
        viewModel.loadCount(cartRepository)

        // then
        val actual = viewModel.cartSize.getOrAwaitValue()
        assertThat(actual).isEqualTo(0)
    }

    @Test
    fun `한 페이지에는 5개의 장바구니 상품이 있다`() {
        // given
        repeat(pageSize) {
            cartRepository.increaseQuantity(it.toLong())
        }

        // when
        viewModel.loadCart(cartRepository, 0, pageSize)

        // then
        val actual = viewModel.cart.getOrAwaitValue()
        assertThat(actual).hasSize(pageSize)
        assertThat(actual).isEqualTo(cartRepository.findRange(0, pageSize))
    }

    @Test
    fun `장바구니 상품이 10개인 경우 5개의 상품을 불러온다`() {
        // given
        repeat(10) {
            cartRepository.increaseQuantity(it.toLong())
        }

        // when
        viewModel.loadCart(cartRepository, 0, pageSize)

        // then
        val actual = viewModel.cart.getOrAwaitValue()
        assertThat(actual).hasSize(pageSize)
        assertThat(actual).isEqualTo(cartRepository.findRange(0, pageSize))
    }

    @Test
    fun `장바구니에 6개의 상품을 담았다면 장바구니 상품 수는 6이다`() {
        // given
        repeat(6) {
            cartRepository.increaseQuantity(it.toLong())
        }

        // when
        viewModel.loadCount(cartRepository)

        // then
        val actual = viewModel.cartSize.getOrAwaitValue()
        assertThat(actual).isEqualTo(6)
    }
}
