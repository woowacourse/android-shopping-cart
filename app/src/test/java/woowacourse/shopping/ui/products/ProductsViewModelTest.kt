package woowacourse.shopping.ui.products

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.FakeCartRepository
import woowacourse.shopping.FakeRecentProductRepository
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.cartItem
import woowacourse.shopping.convertProductUiModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.MockWebServerProductRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.data.product.server.MockWebProductServer
import woowacourse.shopping.data.product.server.MockWebProductServerDispatcher
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.products
import java.lang.IllegalArgumentException

@ExtendWith(InstantTaskExecutorExtension::class)
class ProductsViewModelTest {
    private lateinit var viewModel: ProductsViewModel

    private lateinit var productRepository: ProductRepository
    private lateinit var recentProductRepository: RecentProductRepository
    private lateinit var cartRepository: CartRepository

    private lateinit var productServer: MockWebProductServer

    @BeforeEach
    fun setUp() {
        recentProductRepository = FakeRecentProductRepository()
        cartRepository = FakeCartRepository()
    }

    @AfterEach
    fun tearDown() {
        productServer.shutDown()
    }

    @Test
    fun `한 페이지에는 20개의 상품이 있다`() {
        val products = products(20)
        setUpProductRepository(products)
        viewModel = ProductsViewModel(productRepository, recentProductRepository, cartRepository)

        val actual = viewModel.productUiModels.getOrAwaitValue()
        assertThat(actual).hasSize(20)
        assertThat(actual).isEqualTo(convertProductUiModel(products, cartRepository).take(20))
    }

    @Test
    fun `상품이 40개인 경우 1페이지에서 20개의 상품을 불러온다`() {
        val products = products(40)
        setUpProductRepository(products)
        viewModel = ProductsViewModel(productRepository, recentProductRepository, cartRepository)

        val actual = viewModel.productUiModels.getOrAwaitValue()
        assertThat(actual).hasSize(20)
        assertThat(actual).isEqualTo(convertProductUiModel(products, cartRepository).take(20))
    }

    @Test
    fun `상품이 5개인 경우 1페이지에서 5개의 상품을 불러온다`() {
        val products = products(5)
        setUpProductRepository(products)
        viewModel = ProductsViewModel(productRepository, recentProductRepository, cartRepository)

        val actual = viewModel.productUiModels.getOrAwaitValue()
        assertThat(actual).hasSize(5)
        assertThat(actual).isEqualTo(convertProductUiModel(products, cartRepository).take(5))
    }

    @Test
    fun `상품이 10개이고 1페이지의 10번째 상품에 위치해 있는 경우 상품을 더 불러올 수 없다`() {
        // given
        val products = products(10)
        setUpProductRepository(products)
        viewModel = ProductsViewModel(productRepository, recentProductRepository, cartRepository)

        // when
        viewModel.changeSeeMoreVisibility(9)

        // then
        val actual = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    @Test
    fun `상품이 25개이고 1페이지의 20번째 상품에 위치해 있는 경우 상품을 더 불러올 수 있다`() {
        // given
        val products = products(25)
        setUpProductRepository(products)
        viewModel = ProductsViewModel(productRepository, recentProductRepository, cartRepository)

        // when
        viewModel.changeSeeMoreVisibility(19)

        // then
        val actual = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(actual).isTrue
    }

    @Test
    fun `상품이 25개이고 2페이지로 이동하면 25개의 상품이 보인다`() {
        // given
        val products = products(25)
        setUpProductRepository(products)
        viewModel = ProductsViewModel(productRepository, recentProductRepository, cartRepository)

        // when
        viewModel.loadPage()

        // then
        val actual = viewModel.productUiModels.getOrAwaitValue()
        assertThat(actual).hasSize(25)
        assertThat(actual).isEqualTo(convertProductUiModel(products, cartRepository).take(25))
    }

    @Test
    fun `상품이 25개이고 2페이지의 25번째 상품에 위치해 있는 경우 상품을 더 불러올 수 없다`() {
        // given
        val products = products(25)
        setUpProductRepository(products)
        viewModel = ProductsViewModel(productRepository, recentProductRepository, cartRepository)

        // when
        viewModel.loadPage()
        viewModel.changeSeeMoreVisibility(24)

        // then
        val actual = viewModel.showLoadMore.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    @Test
    fun `장바구니에 담겨지지 않은 상품의 개수를 증가시키면 장바구니에 담긴다`() {
        // given
        val products = products(10)
        setUpProductRepository(products)
        viewModel = ProductsViewModel(productRepository, recentProductRepository, cartRepository)

        // when
        viewModel.increaseQuantity(0L)

        // then
        val actual = cartRepository.find(0L)
        assertThat(actual.quantity).isEqualTo(Quantity(1))
        assertThat(viewModel.cartTotalCount.getOrAwaitValue()).isEqualTo(1)
    }

    @Test
    fun `장바구니에 담겨진 상품의 개수를 3개에서 4개로 증가시킨다`() {
        // given
        val products = products(10)
        setUpProductRepository(products)
        cartRepository = FakeCartRepository(listOf(cartItem(0L, Quantity(3))))
        viewModel = ProductsViewModel(productRepository, recentProductRepository, cartRepository)

        // when
        viewModel.increaseQuantity(0L)

        // then
        val actual = cartRepository.find(0L)
        assertThat(actual.quantity).isEqualTo(Quantity(4))
        assertThat(viewModel.cartTotalCount.getOrAwaitValue()).isEqualTo(4)
    }

    @Test
    fun `장바구니에 1개가 담겨진 상품의 개수를 감소시키면 장바구니에서 삭제된다`() {
        // given
        val products = products(10)
        setUpProductRepository(products)
        cartRepository = FakeCartRepository(listOf(cartItem(0L, Quantity(1))))
        viewModel = ProductsViewModel(productRepository, recentProductRepository, cartRepository)

        // when
        viewModel.decreaseQuantity(0L)

        // then
        assertThrows<IllegalArgumentException> { cartRepository.find(0L) }
        assertThat(viewModel.cartTotalCount.getOrAwaitValue()).isEqualTo(0)
    }

    @Test
    fun `장바구니에 담겨진 상품의 개수를 6개에서 5개로 감소시킨다`() {
        // given
        val products = products(10)
        setUpProductRepository(products)
        cartRepository = FakeCartRepository(listOf(cartItem(0L, Quantity(6))))
        viewModel = ProductsViewModel(productRepository, recentProductRepository, cartRepository)

        // when
        viewModel.decreaseQuantity(0L)

        // then
        val actual = cartRepository.find(0L)
        assertThat(actual.quantity).isEqualTo(Quantity(5))
        assertThat(viewModel.cartTotalCount.getOrAwaitValue()).isEqualTo(5)
    }

    private fun setUpProductRepository(products: List<Product>) {
        productServer = MockWebProductServer(MockWebProductServerDispatcher(products))
        productServer.start()
        productRepository = MockWebServerProductRepository(productServer)
    }
}
