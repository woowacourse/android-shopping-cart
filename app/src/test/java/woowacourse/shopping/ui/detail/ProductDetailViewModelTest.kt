package woowacourse.shopping.ui.detail

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.FakeCartRepository
import woowacourse.shopping.FakeRecentProductRepository
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.MockWebServerProductRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.server.MockWebProductServer
import woowacourse.shopping.data.product.server.MockWebProductServerDispatcher
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.data.recent.entity.RecentProduct
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.products
import java.time.LocalDateTime

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductDetailViewModelTest {
    private lateinit var viewModel: ProductDetailViewModel

    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductRepository: RecentProductRepository
    private val cartRepository: CartRepository = FakeCartRepository()

    private lateinit var productServer: MockWebProductServer

    @BeforeEach
    fun setUp() {
        productServer = MockWebProductServer(MockWebProductServerDispatcher(products(2)))
        productServer.start()
        productRepository = MockWebServerProductRepository(productServer)
        recentProductRepository = FakeRecentProductRepository()
    }

    @AfterEach
    fun tearDown() {
        productServer.shutDown()
    }

    @Test
    fun `상품 id에 맞는 상품을 불러온다`() {
        viewModel =
            ProductDetailViewModel(
                0L,
                productRepository,
                recentProductRepository,
                cartRepository,
                false,
            )

        val actual = viewModel.productUiModel.getOrAwaitValue()
        assertThat(actual.productId).isEqualTo(0L)
    }

    @Test
    fun `상품 id에 해당하는 상품이 없는 경우 에러가 발생한다`() {
        viewModel =
            ProductDetailViewModel(
                -1L,
                productRepository,
                recentProductRepository,
                cartRepository,
                false,
            )
        assertThat(viewModel.productLoadError.getOrAwaitValue().peekContent()).isNotNull
    }

    @Test
    fun `상품을 장바구니에 담는다`() {
        // given
        viewModel =
            ProductDetailViewModel(
                0L,
                productRepository,
                recentProductRepository,
                cartRepository,
                false,
            )

        // when
        viewModel.addCartProduct()

        // then
        val actual = cartRepository.find(0L)
        assertThat(actual.productId).isEqualTo(0L)
        assertThat(viewModel.isSuccessAddCart.getOrAwaitValue().peekContent()).isTrue
    }

    @Test
    fun `마지막으로 본 상품을 불러온다`() {
        setUpRecentProductRepository(productId = 1L)
        viewModel =
            ProductDetailViewModel(
                0L,
                productRepository,
                recentProductRepository,
                cartRepository,
                false,
            )

        val actual = viewModel.lastRecentProduct.getOrAwaitValue()
        assertThat(actual.productId).isEqualTo(1L)
    }

    @Test
    fun `마지막으로 본 상품이 없는 경우 불러올 수 없다`() {
        viewModel =
            ProductDetailViewModel(
                0L,
                productRepository,
                recentProductRepository,
                cartRepository,
                false,
            )

        val actual = viewModel.lastRecentProduct.isInitialized
        assertThat(actual).isFalse
    }

    @Test
    fun `마지막으로 본 상품이 현재 상품과 동일힌 경우 마지막으로 본 상품이 보이지 않는다`() {
        setUpRecentProductRepository(productId = 0L)
        viewModel =
            ProductDetailViewModel(
                0L,
                productRepository,
                recentProductRepository,
                cartRepository,
                false,
            )

        val actual = viewModel.isVisibleLastRecentProduct.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    @Test
    fun `마지막으로 본 상품을 타고 들어온 경우 마지막으로 본 상품이 보이지 않는다`() {
        setUpRecentProductRepository(productId = 1L)
        viewModel =
            ProductDetailViewModel(
                0L,
                productRepository,
                recentProductRepository,
                cartRepository,
                true,
            )

        val actual = viewModel.isVisibleLastRecentProduct.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    private fun setUpRecentProductRepository(productId: Long) {
        recentProductRepository =
            FakeRecentProductRepository(listOf(RecentProduct(0L, productId, LocalDateTime.now())))
    }
}
