package woowacourse.shopping.ui.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.FakeCartRepository
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.cartItem
import woowacourse.shopping.cartItems
import woowacourse.shopping.convertProductUiModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.MockWebServerProductRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.entity.Product
import woowacourse.shopping.data.product.server.MockWebProductServer
import woowacourse.shopping.data.product.server.MockWebProductServerDispatcher
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.product
import woowacourse.shopping.products

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel

    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository

    private lateinit var productServer: MockWebProductServer

    @AfterEach
    fun tearDown() {
        productServer.shutDown()
    }

    @Test
    fun `장바구니에 담긴 상품을 삭제한다`() {
        // given
        val cartItems = cartItems(1)
        setUpProductRepository(products(1))
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(productRepository, cartRepository)

        // when
        viewModel.deleteCartItem(cartItems.first().productId)

        // then
        assertThat(viewModel.productUiModels.getOrAwaitValue()).hasSize(0)
    }

    @Test
    fun `한 페이지에는 5개의 장바구니 상품이 있다`() {
        // given
        val cartItems = cartItems(5)
        val products = products(5)
        setUpProductRepository(products)
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(productRepository, cartRepository)

        // then
        val actual = viewModel.productUiModels.getOrAwaitValue()
        assertThat(actual).hasSize(5)
        assertThat(actual).isEqualTo(convertProductUiModel(cartItems, products).take(5))
    }

    @Test
    fun `장바구니에 3개의 상품이 있는 경우 1페이지에는 3개의 상품이 보인다`() {
        // given
        val cartItems = cartItems(3)
        val products = products(3)
        setUpProductRepository(products)
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(productRepository, cartRepository)

        // then
        val actual = viewModel.productUiModels.getOrAwaitValue()
        assertThat(actual).hasSize(3)
        assertThat(actual).isEqualTo(convertProductUiModel(cartItems, products).take(3))
    }

    @Test
    fun `장바구니에 5개의 상품이 있는 경우 페이지 이동 버튼이 보이지 않는다`() {
        // given
        val cartItems = cartItems(5)
        setUpProductRepository(products(5))
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(productRepository, cartRepository)

        // then
        val actual = viewModel.hasPage.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    @Test
    fun `장바구니 상품이 6개인 경우 1페이지에는 5개의 상품이 보인다`() {
        // given
        val cartItems = cartItems(6)
        val products = products(6)
        setUpProductRepository(products)
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(productRepository, cartRepository)

        // then
        val actual = viewModel.productUiModels.getOrAwaitValue()
        assertThat(actual).hasSize(5)
        assertThat(actual).isEqualTo(convertProductUiModel(cartItems, products).take(5))
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 1페이지인 경우 다음 페이지로 이동할 수 있다`() {
        // given
        val cartItems = cartItems(6)
        setUpProductRepository(products(6))
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(productRepository, cartRepository)

        // then
        val actual = viewModel.hasNextPage.getOrAwaitValue()
        assertThat(actual).isTrue
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 1페이지인 경우 이전 페이지로 이동할 수 없다`() {
        // given
        val cartItems = cartItems(6)
        setUpProductRepository(products(6))
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(productRepository, cartRepository)

        // then
        val actual = viewModel.hasPreviousPage.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 2페이지인 경우 이전 페이지로 이동할 수 있다`() {
        // given
        val cartItems = cartItems(6)
        setUpProductRepository(products(6))
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(productRepository, cartRepository)

        // when
        viewModel.moveNextPage()

        // then
        val actual = viewModel.hasPreviousPage.getOrAwaitValue()
        assertThat(actual).isTrue
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 2페이지인 경우 다음 페이지로 이동할 수 없다`() {
        // given
        val cartItems = cartItems(6)
        setUpProductRepository(products(6))
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(productRepository, cartRepository)

        // when
        viewModel.moveNextPage()

        // then
        val actual = viewModel.hasNextPage.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 1페이지에서 2페이지로 이동하면 1개의 상품이 보인다`() {
        // given
        val cartItems = cartItems(6)
        val products = products(6)
        setUpProductRepository(products(6))
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(productRepository, cartRepository)

        // when
        viewModel.moveNextPage()

        // then
        val actual = viewModel.productUiModels.getOrAwaitValue()
        assertThat(actual).hasSize(1)
        assertThat(actual).isEqualTo(convertProductUiModel(cartItems, products).slice(5..5))
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 2페이지에서 1페이지로 이동하면 5개의 상품이 보인다`() {
        // given
        val cartItems = cartItems(6)
        val products = products(6)
        setUpProductRepository(products)
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(productRepository, cartRepository)
        viewModel.moveNextPage()

        // when
        viewModel.movePreviousPage()

        // then
        val actual = viewModel.productUiModels.getOrAwaitValue()
        assertThat(actual).hasSize(5)
        assertThat(actual).isEqualTo(convertProductUiModel(cartItems, products).take(5))
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 2페이지에서 1개의 상품을 삭제하면 1페이지로 이동한다`() {
        // given
        val cartItems = cartItems(6)
        setUpProductRepository(products(6))
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(productRepository, cartRepository)
        viewModel.moveNextPage()

        // when
        val lastOneCartItem = viewModel.productUiModels.getOrAwaitValue().first()
        viewModel.deleteCartItem(lastOneCartItem.productId)

        // then
        val actual = viewModel.page.getOrAwaitValue()
        assertThat(actual).isEqualTo(0)
    }

    @Test
    fun `장바구니에 1개의 상품이 있고 1개의 상품을 삭제하면 장바구니가 비어있다`() {
        // given
        val cartItems = cartItems(1)
        setUpProductRepository(products(1))
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(productRepository, cartRepository)

        // when
        val cartItem = viewModel.productUiModels.getOrAwaitValue().first()
        viewModel.deleteCartItem(cartItem.productId)

        // then
        val actual = viewModel.isEmptyCart.getOrAwaitValue()
        assertThat(actual).isTrue
    }

    @Test
    fun `장바구니에 담겨있는 상품의 개수를 2개에서 3개로 증가시킨다`() {
        // given
        val cartItem = cartItem(0L, Quantity(2))
        val product = product(0L)
        setUpProductRepository(listOf(product))
        cartRepository = FakeCartRepository(listOf(cartItem))
        viewModel = CartViewModel(productRepository, cartRepository)

        // when
        viewModel.increaseQuantity(0L)

        // then
        val actual = viewModel.productUiModels.getOrAwaitValue().first()
        assertThat(actual.quantity).isEqualTo(Quantity(3))
    }

    @Test
    fun `장바구니에 담겨있는 상품의 개수를 3개에서 2개로 감소시킨다`() {
        // given
        val cartItem = cartItem(0L, Quantity(3))
        val product = product(0L)
        setUpProductRepository(listOf(product))
        cartRepository = FakeCartRepository(listOf(cartItem))
        viewModel = CartViewModel(productRepository, cartRepository)

        // when
        viewModel.decreaseQuantity(0L)

        // then
        val actual = viewModel.productUiModels.getOrAwaitValue().first()
        assertThat(actual.quantity).isEqualTo(Quantity(2))
    }

    @Test
    fun `장바구니에 담겨있는 상품의 개수를 1개에서 0개로 감소시키는 경우 장바구니에서 상품이 삭제된다`() {
        // given
        val cartItem = cartItem(0L, Quantity(1))
        val product = product(0L)
        setUpProductRepository(listOf(product))
        cartRepository = FakeCartRepository(listOf(cartItem))
        viewModel = CartViewModel(productRepository, cartRepository)

        // when
        viewModel.decreaseQuantity(0L)

        // then
        val actual = viewModel.isEmptyCart.getOrAwaitValue()
        assertThat(actual).isTrue
    }

    private fun setUpProductRepository(products: List<Product>) {
        productServer = MockWebProductServer(MockWebProductServerDispatcher(products))
        productServer.start()
        productRepository = MockWebServerProductRepository(productServer)
    }
}
