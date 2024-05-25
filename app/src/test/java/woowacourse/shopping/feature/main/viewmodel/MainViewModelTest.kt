package woowacourse.shopping.feature.main.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private val productRepository: ProductRepository = ProductDummyRepository
    private val cartRepository: CartRepository = CartDummyRepository
    private val pageSize: Int = 20

    @BeforeEach
    fun setUp() {
        viewModel = MainViewModel(productRepository, cartRepository)
        productRepository.deleteAll()
    }

    @Test
    fun `한 페이지에는 20개의 상품이 있다`() {
        // given
        repeat(pageSize) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage(0, pageSize)

        // then
        val products = viewModel.products.getOrAwaitValue()
        assertAll(
            { assertThat(products).hasSize(pageSize) },
            { assertThat(products).isEqualTo(productRepository.findRange(0, pageSize)) },
        )
    }

    @Test
    fun `상품이 40개인 경우 20개의 상품을 불러온다`() {
        // given
        repeat(40) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage(0, pageSize)

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertAll(
            { assertThat(actual).hasSize(pageSize) },
            { assertThat(actual).isEqualTo(productRepository.findRange(0, pageSize)) },
        )
    }

    @Test
    fun `상품이 5개인 경우 5개의 상품을 불러온다`() {
        // given
        repeat(5) {
            productRepository.save(imageUrl, title, price)
        }

        // when
        viewModel.loadPage(0, pageSize)

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertAll(
            { assertThat(actual).hasSize(5) },
            { assertThat(actual).isEqualTo(productRepository.findRange(0, pageSize)) },
        )
    }

    @Test
    fun `상품을 장바구니에 2번 담으면 장바구니에 담긴 해당 상품의 수량은 2이다`() {
        val productId = productRepository.save(imageUrl, title, price)
        viewModel.loadPage(page = 0, pageSize)

        repeat(2) {
            viewModel.addProductToCart(productId)
        }

        val actual = viewModel.quantities.getOrAwaitValue().first { it.productId == productId }
        assertThat(actual.quantity.count).isEqualTo(2)
    }

    @Test
    fun `장바구니에 담긴 한 상품의 수량이 3인 상태에서 상품을 1개 빼면 장바구니에 담긴 해당 상품의 수량은 2이다`() {
        val productId = productRepository.save(imageUrl, title, price)
        viewModel.loadPage(page = 0, pageSize)
        repeat(3) {
            viewModel.addProductToCart(productId)
        }

        viewModel.deleteProductToCart(productId)

        val actual = viewModel.quantities.getOrAwaitValue().first { it.productId == productId }
        assertThat(actual.quantity.count).isEqualTo(2)
    }
}
