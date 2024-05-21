package woowacourse.shopping.feature.detail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.FakeCartRepository
import woowacourse.shopping.data.product.FakeProductRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.imageUrl
import woowacourse.shopping.model.Product
import woowacourse.shopping.price
import woowacourse.shopping.title

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var productRepository: ProductRepository
    private val cartRepository: CartRepository = FakeCartRepository()

    @BeforeEach
    fun setUp() {
        productRepository = FakeProductRepository(listOf(product(0L)))
    }

    @Test
    fun `상품 id에 맞는 상품을 불러온다`() {
        viewModel = ProductDetailViewModel(0L, productRepository, cartRepository)

        val actual = viewModel.product.getOrAwaitValue()
        actual.assertThat(imageUrl, title, price)
    }

    @Test
    fun `상품 id에 해당하는 상품이 없는 경우 에러가 발생한다`() {
        viewModel = ProductDetailViewModel(-1L, productRepository, cartRepository)

        assertThat(viewModel.productLoadError.getOrAwaitValue().peekContent()).isTrue
    }

    @Test
    fun `상품을 장바구니에 담는다`() {
        // given
        viewModel = ProductDetailViewModel(0L, productRepository, cartRepository)

        // when
        viewModel.addCartProduct()

        // then
        val actual = cartRepository.findRange(0, 5).first()
        actual.product.assertThat(imageUrl, title, price)
        assertThat(actual.quantity.count).isEqualTo(1)
        assertThat(viewModel.isSuccessAddCart.getOrAwaitValue().peekContent()).isTrue
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

    private fun product(id: Long) = Product(id, imageUrl, title, price)
}
