package woowacourse.shopping.feature.detail.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.feature.InstantTaskExecutorExtension
import woowacourse.shopping.feature.getOrAwaitValue
import woowacourse.shopping.imageUrl
import woowacourse.shopping.price
import woowacourse.shopping.title
import java.lang.IllegalArgumentException

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel
    private val productRepository: ProductRepository = ProductDummyRepository
    private val cartRepository: CartRepository = CartDummyRepository

    @BeforeEach
    fun setUp() {
        viewModel = ProductDetailViewModel(productRepository, cartRepository)
        productRepository.deleteAll()
        cartRepository.deleteAll()
    }

    @Test
    fun `상품 id에 맞는 상품을 불러온다`() {
        // given
        val id = productRepository.save(imageUrl, title, price)

        // when
        viewModel.loadProduct(id)

        // then
        val actual = viewModel.product.getOrAwaitValue()
        assertAll(
            { assertThat(actual.imageUrl).isEqualTo(imageUrl) },
            { assertThat(actual.title).isEqualTo(title) },
            { assertThat(actual.price).isEqualTo(price) },
        )
    }

    @Test
    fun `상품 id에 해당하는 상품이 없는 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            viewModel.loadProduct(-1L)
        }
    }

    @Test
    fun `장바구니에 상품을 추가하지 않았을 때 장바구니 상품 추가 성공 여부는 false 이다`() {
        val productId = productRepository.save(imageUrl, title, price)
        viewModel.loadProduct(productId)

        val actual = viewModel.isSuccessAddToCart.getOrAwaitValue()
        assertThat(actual).isFalse()
    }

    @Test
    fun `장바구니에 상품을 추가했을 때 장바구니 상품 추가 성공 여부는 true 이다`() {
        val productId = productRepository.save(imageUrl, title, price)
        viewModel.loadProduct(productId)

        viewModel.addProductToCart(productId)

        val actual = viewModel.isSuccessAddToCart.getOrAwaitValue()
        assertThat(actual).isTrue()
    }

    @Test
    fun `장바구니에 담겨 있지 않은 상품이라면 기본 수량은 1이다`() {
        val productId = productRepository.save(imageUrl, title, price)
        viewModel.loadProduct(productId)

        val actual = viewModel.quantity.getOrAwaitValue()
        assertThat(actual.count).isEqualTo(1)
    }

    @Test
    fun `장바구니에 담겨 있는 상품이라면 장바구니 수량을 불러온다`() {
        val productId = productRepository.save(imageUrl, title, price)
        cartRepository.addCartItem(productId, count = 3)
        viewModel.loadProduct(productId)

        val actual = viewModel.quantity.getOrAwaitValue()
        assertThat(actual.count).isEqualTo(3)
    }

    @Test
    fun `상품의 수량이 1일 때 상품 수를 증가시키면 상품의 수량은 2가 된다`() {
        val productId = productRepository.save(imageUrl, title, price)
        viewModel.loadProduct(productId)

        viewModel.increaseQuantity()

        val actual = viewModel.quantity.getOrAwaitValue()
        assertThat(actual.count).isEqualTo(2)
    }

    @Test
    fun `상품의 수량이 4일 때 상품 수를 감소시키면 상품의 수량은 3이 된다`() {
        val productId = productRepository.save(imageUrl, title, price)
        viewModel.loadProduct(productId)

        repeat(3) {
            viewModel.increaseQuantity()
        }
        viewModel.decreaseQuantity()

        val actual = viewModel.quantity.getOrAwaitValue()
        assertThat(actual.count).isEqualTo(3)
    }
}
