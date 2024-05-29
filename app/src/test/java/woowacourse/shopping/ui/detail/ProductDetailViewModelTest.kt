package woowacourse.shopping.ui.detail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.OrdersRepository
import woowacourse.shopping.model.data.ProductMockWebServer
import woowacourse.shopping.model.data.ProductRepositoryImpl
import woowacourse.shopping.model.data.ProductsImpl
import woowacourse.shopping.ui.FakeOrderDao
import woowacourse.shopping.ui.FakeRecentProductDao

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel
    private val productRepository = ProductRepositoryImpl(ProductMockWebServer())
    private val ordersRepository = OrdersRepository(FakeOrderDao)

    @BeforeEach
    fun setUp() {
        productRepository.start()
        viewModel = ProductDetailViewModel(ProductsImpl, FakeOrderDao, FakeRecentProductDao)
    }

    @AfterEach
    fun tearDown() {
        productRepository.shutdown()
    }

    @Test
    fun `선택한 상품이 불러와진다`() {
        // given
        val productId = ProductsImpl.save(product)

        // when
        viewModel.loadProduct(productId)

        // then
        assertEquals(viewModel.product.getOrAwaitValue(), product.copy(id = productId))
    }

    @Test
    fun `상품이 장바구니에 담긴다`() {
        // given
        val productId = ProductsImpl.save(product)
        viewModel.loadProduct(productId)

        // when
        viewModel.addProductToCart()

        // then
        val orderEntity = ordersRepository.getById(0)
        val actual = ProductsImpl.find(orderEntity.productId)
        assertThat(actual.name).isEqualTo(product.name)
    }

    companion object {
        private val product = Product(imageUrl = "", name = "맥북", price = 100)
    }
}
