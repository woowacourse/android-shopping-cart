package woowacourse.shopping.feature.main.viewmodel

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryDao
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryLocalRepository
import woowacourse.shopping.data.inquiryhistory.InquiryHistoryRepository
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
    private val cartDao = mockk<CartDao>()
    private val inquiryHistoryDao = mockk<InquiryHistoryDao>()
    private val productRepository: ProductRepository = ProductDummyRepository
    private val cartRepository: CartRepository = CartDummyRepository(cartDao)
    private val inquiryHistoryRepository: InquiryHistoryRepository = InquiryHistoryLocalRepository(inquiryHistoryDao)
    private val pageSize: Int = 20

    @BeforeEach
    fun setUp() {
        productRepository.deleteAll()
        cartRepository.deleteCartItem(productId = 0L)
    }

    @Test
    fun `한 페이지에는 20개의 상품이 있다`() {
        repeat(40) {
            productRepository.save(imageUrl, title, price)
        }
        viewModel = MainViewModel(productRepository, cartRepository, inquiryHistoryRepository)

        // then
        val products = viewModel.products.getOrAwaitValue()
        assertAll(
            { assertThat(products).hasSize(pageSize) },
            { assertThat(products).isEqualTo(productRepository.findRange(page = 0, pageSize)) },
        )
    }

    @Test
    fun `상품이 40개인 경우 20개의 상품을 불러온다`() {
        repeat(40) {
            productRepository.save(imageUrl, title, price)
        }
        viewModel = MainViewModel(productRepository, cartRepository, inquiryHistoryRepository)

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertAll(
            { assertThat(actual).hasSize(pageSize) },
            { assertThat(actual).isEqualTo(productRepository.findRange(page = 0, pageSize)) },
        )
    }

    @Test
    fun `상품이 5개인 경우 5개의 상품을 불러온다`() {
        repeat(5) {
            productRepository.save(imageUrl, title, price)
        }
        viewModel = MainViewModel(productRepository, cartRepository, inquiryHistoryRepository)

        // then
        val actual = viewModel.products.getOrAwaitValue()
        assertAll(
            { assertThat(actual).hasSize(5) },
            { assertThat(actual).isEqualTo(productRepository.findRange(page = 0, 20)) },
        )
    }

    @Test
    fun `상품을 장바구니에 2번 담으면 장바구니에 담긴 해당 상품의 수량은 2이다`() {
        val productId: Long = productRepository.save(imageUrl, title, price)
        viewModel = MainViewModel(productRepository, cartRepository, inquiryHistoryRepository)

        repeat(2) {
            viewModel.addProductToCart(productId = productId)
        }

        val actual = viewModel.quantities.getOrAwaitValue().first { it.productId == productId }
        assertThat(actual.quantity.count).isEqualTo(2)
    }

    @Test
    fun `장바구니에 담긴 한 상품의 수량이 3인 상태에서 상품을 1개 빼면 장바구니에 담긴 해당 상품의 수량은 2이다`() {
        val productId: Long = productRepository.save(imageUrl, title, price)
        viewModel = MainViewModel(productRepository, cartRepository, inquiryHistoryRepository)

        repeat(3) {
            viewModel.addProductToCart(productId = productId)
        }

        viewModel.deleteProductToCart(productId = productId)

        val actual = viewModel.quantities.getOrAwaitValue().first { it.productId == productId }
        assertThat(actual.quantity.count).isEqualTo(2)
    }
}
