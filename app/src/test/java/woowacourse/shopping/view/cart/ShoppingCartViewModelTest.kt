package woowacourse.shopping.view.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.FakeCartRepository
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.TestFixture.getOrAwaitValue
import woowacourse.shopping.data.product.mockserver.MockProductService
import woowacourse.shopping.data.product.mockserver.MockServer
import woowacourse.shopping.data.product.mockserver.MockServerProductRepository
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.ProductUpdate
import kotlin.concurrent.thread

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var cartRepository: CartRepository

    private lateinit var viewModel: ShoppingCartViewModel

    @BeforeEach
    fun setUp() {
        thread {
            MockServer.mockWebServer.start(12345)
        }.join()

        productRepository = MockServerProductRepository(MockProductService())
    }

    @AfterEach
    fun tearDown() {
        thread {
            MockServer.mockWebServer.shutdown()
        }.join()
    }

    @Test
    fun `장바구니에_담긴_아이템이_한_화면에_담을_수_있는_최대_개수_이하라면_모든_페이지_컨트롤이_비활성화된다`() {
        val cartItemSetting =
            List(2) {
                CartItem(it.toLong(), Product(it), quantity = 1)
            }
        generateViewModel(cartItemSetting)

        assertThat(viewModel.isPrevButtonActivated.getOrAwaitValue()).isEqualTo(false)
        assertThat(viewModel.isNextButtonActivated.getOrAwaitValue()).isEqualTo(false)
    }

    @Test
    fun `장바구니에_담긴_아이템이_한_화면에_담을_수_있는_최대_개수_초과라면_다음_페이지_컨트롤이_활성화된다`() {
        val cartItemSetting =
            List(5) {
                CartItem(it.toLong(), Product(it), quantity = 1)
            }
        generateViewModel(cartItemSetting)

        assertThat(viewModel.isPrevButtonActivated.getOrAwaitValue()).isEqualTo(false)
        assertThat(viewModel.isNextButtonActivated.getOrAwaitValue()).isEqualTo(true)
    }

    @Test
    fun `장바구니에_담긴_아이템의_개수를_증가시킬_수_있다`() {
        val product = Product(id = 0)
        val cartItemSetting = listOf(CartItem(0L, product, quantity = 1))
        generateViewModel(cartItemSetting)

        viewModel.onIncreaseQuantityButtonClicked(product)

        val expected = ProductUpdate(productId = 0L, updatedQuantity = 2)
        assertThat(viewModel.updatedCountInfo.getOrAwaitValue()).isEqualTo(expected)
    }

    @Test
    fun `장바구니에_담긴_아이템의_개수를_감소시킬_수_있다`() {
        val product = Product(id = 0)
        val cartItemSetting = listOf(CartItem(0L, product, quantity = 3))
        generateViewModel(cartItemSetting)

        viewModel.onDecreaseQuantityButtonClicked(product)

        val expected = ProductUpdate(productId = 0L, updatedQuantity = 2)
        assertThat(viewModel.updatedCountInfo.getOrAwaitValue()).isEqualTo(expected)
    }

    private fun generateViewModel(initialCartItems: List<CartItem>) {
        cartRepository = FakeCartRepository(initialCartItems)
        viewModel = ShoppingCartViewModel(productRepository, cartRepository)
    }
}

private fun Product(id: Int): Product {
    return Product(id.toLong(), "product $id", 1_000, "")
}
