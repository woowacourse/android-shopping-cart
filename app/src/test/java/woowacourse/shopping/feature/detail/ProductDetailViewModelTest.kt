package woowacourse.shopping.feature.detail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.feature.detail.viewmodel.ProductDetailViewModel
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.imageUrl
import woowacourse.shopping.model.Product
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
        actual.assertThat(imageUrl, title, price)
    }

    @Test
    fun `상품 id에 해당하는 상품이 없는 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            viewModel.loadProduct(-1L)
        }
    }

    @Test
    fun `상품을 장바구니에 담는다`() {
        // given
        val id = productRepository.save(imageUrl, title, price)
        viewModel.loadProduct(id)

        // when
        viewModel.addCartProduct()

        // then
        val actual = cartRepository.findAll().first()
        actual.product.assertThat(imageUrl, title, price)
        assertThat(actual.quantity.count).isEqualTo(1)
    }

    private fun Product.assertThat(
        expectedImageUrl: String,
        expectedTitle: String,
        expectedPrice: Int,
    ) {
        assertThat(imageUrl).isEqualTo(expectedImageUrl)
        assertThat(title).isEqualTo(expectedTitle)
        assertThat(price).isEqualTo(expectedPrice)
    }
}
