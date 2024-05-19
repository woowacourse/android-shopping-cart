package woowacourse.shopping.feature.detail.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.feature.InstantTaskExecutorExtension
import woowacourse.shopping.feature.cart.viewmodel.CartViewModel
import woowacourse.shopping.feature.getOrAwaitValue
import woowacourse.shopping.imageUrl
import woowacourse.shopping.price
import woowacourse.shopping.title
import java.lang.IllegalArgumentException

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var productDetailViewModel: ProductDetailViewModel
    private lateinit var cartViewModel: CartViewModel
    private val productRepository: ProductRepository = ProductDummyRepository
    private val cartRepository: CartRepository = CartDummyRepository

    @BeforeEach
    fun setUp() {
        productDetailViewModel = ProductDetailViewModel(productRepository, cartRepository)
        cartViewModel = CartViewModel(cartRepository)
        productRepository.deleteAll()
        cartRepository.deleteAll()
    }

    @Test
    fun `상품 id에 맞는 상품을 불러온다`() {
        // given
        val id = productRepository.save(imageUrl, title, price)

        // when
        productDetailViewModel.loadProduct(id)

        // then
        val actual = productDetailViewModel.product.getOrAwaitValue()
        assertAll(
            Executable { assertThat(actual.imageUrl).isEqualTo(imageUrl) },
            Executable { assertThat(actual.title).isEqualTo(title) },
            Executable { assertThat(actual.price).isEqualTo(price) },
        )
    }

    @Test
    fun `상품 id에 해당하는 상품이 없는 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            productDetailViewModel.loadProduct(-1L)
        }
    }

    @Test
    fun `장바구니에 상품을 추가한다`() {
        val id = productRepository.save(imageUrl, title, price)
        productDetailViewModel.loadProduct(id)

        productDetailViewModel.addProductToCart()
        cartViewModel.loadCount()

        val actual = cartViewModel.cartSize.getOrAwaitValue()
        assertThat(actual).isEqualTo(1)
    }
}
