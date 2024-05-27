package woowacourse.shopping.ui.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.api.ProductMockWebServer
import woowacourse.shopping.data.cart.Cart
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.product.ProductRepositoryImpl
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.ui.FakeCartDao
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private val productRepository = ProductRepositoryImpl(ProductMockWebServer())
    private val cartRepository = CartRepositoryImpl.get(FakeCartDao)

    @BeforeEach
    fun setUp() {
        productRepository.start()
        cartRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        productRepository.shutdown()
    }

    @Test
    fun `장바구니에 상품을 넣지 않았다면 장바구니가 비어있어야 한다`() {
        // when
        viewModel = CartViewModel(productRepository, cartRepository)

        // then
        assertThat(viewModel.productWithQuantity.getOrAwaitValue().size).isEqualTo(0)
    }

    @Test
    fun `장바구니에 상품을 담으면 장바구니 화면에서 보여야 한다`() {
        // given
        cartRepository.insert(Cart(productId = 0L))

        // when
        viewModel = CartViewModel(productRepository, cartRepository)

        // then
        assertThat(viewModel.productWithQuantity.getOrAwaitValue()[0].product.name).isEqualTo("맥북0")
    }

    @Test
    fun `상품을 지울 수 있어야 한다`() {
        // given
        cartRepository.insert(Cart(productId = 0L))

        // when
        viewModel = CartViewModel(productRepository, cartRepository)
        viewModel.removeCartItem(0L)

        // then
        assertThat(viewModel.productWithQuantity.getOrAwaitValue().size).isEqualTo(0)
    }
}
