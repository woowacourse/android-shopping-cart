package woowacourse.shopping.ui.products

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.api.ProductMockWebServer
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.data.recentproduct.RecentProductRepositoryImpl
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.ui.FakeCartDao
import woowacourse.shopping.ui.FakeRecentProductDao
import woowacourse.shopping.ui.products.viewmodel.ProductContentsViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductContentsViewModelTest {
    private lateinit var viewModel: ProductContentsViewModel
    private val productRepository = ProductRepositoryImpl(ProductMockWebServer())
    private val recentProductRepository = RecentProductRepositoryImpl.get(FakeRecentProductDao)
    private val cartRepository = CartRepositoryImpl.get(FakeCartDao)

    @BeforeEach
    fun setUp() {
        cartRepository.deleteAll()
        productRepository.start()
    }

    @AfterEach
    fun tearDown() {
        productRepository.shutdown()
    }

    @Test
    fun `상품은 한 화면에 20개까지만 보여져야 한다`() {
        // given

        // when
        viewModel =
            ProductContentsViewModel(productRepository, recentProductRepository, cartRepository)

        // then
        assertThat(viewModel.productWithQuantity.getOrAwaitValue().size).isEqualTo(20)
    }

    @Test
    fun `첫번째 상품은 맥북0이어야 한다`() {
        // given

        // when
        viewModel =
            ProductContentsViewModel(productRepository, recentProductRepository, cartRepository)

        // then
        assertThat(viewModel.productWithQuantity.getOrAwaitValue()[0].product.name).isEqualTo("맥북0")
    }
}
