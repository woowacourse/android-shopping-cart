package woowacourse.shopping.ui.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.model.data.OrderEntity
import woowacourse.shopping.model.data.OrdersRepository
import woowacourse.shopping.model.data.ProductMockWebServer
import woowacourse.shopping.model.data.ProductRepositoryImpl
import woowacourse.shopping.model.data.ProductsImpl
import woowacourse.shopping.ui.FakeOrderDao

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private val productRepository = ProductRepositoryImpl(ProductMockWebServer())
    private val orderRepository = OrdersRepository(FakeOrderDao)

    @BeforeEach
    fun setUp() {
        productRepository.start()
        viewModel = CartViewModel(ProductsImpl, FakeOrderDao)
    }

    @AfterEach
    fun tearDown() {
        productRepository.shutdown()
    }

    @Test
    fun `장바구니에 상품을 넣지 않았다면 장바구니가 비어있어야 한다`() {
        // when
        viewModel.loadCartItems()

        // then
        assertThat(viewModel.cart.getOrAwaitValue().size).isEqualTo(0)
    }

    @Test
    fun `장바구니에 상품을 담으면 장바구니 화면에서 보여야 한다`() {
        // given
        orderRepository.insert(OrderEntity(0, 1))
        orderRepository.insert(OrderEntity(1, 1))
        orderRepository.insert(OrderEntity(2, 1))

        // when
        viewModel.loadCartItems()

        // then
        assertThat(viewModel.cart.getOrAwaitValue()[0]?.name).isEqualTo("맥북0")
        assertThat(viewModel.cart.getOrAwaitValue()[1]?.name).isEqualTo("아이폰0")
        assertThat(viewModel.cart.getOrAwaitValue()[2]?.name).isEqualTo("갤럭시북0")
    }

    @Test
    fun `상품을 지울 수 있어야 한다`() {
        // given
        orderRepository.insert(OrderEntity(0, 1))
        orderRepository.insert(OrderEntity(1, 1))
        orderRepository.insert(OrderEntity(2, 1))

        // when
        viewModel.loadCartItems()
        viewModel.removeCartItem(1)

        // then
        assertThat(viewModel.cart.getOrAwaitValue()[0]?.name).isEqualTo("맥북0")
        assertThat(viewModel.cart.getOrAwaitValue()[1]?.name).isEqualTo(null)
        assertThat(viewModel.cart.getOrAwaitValue()[2]?.name).isEqualTo("갤럭시북0")
    }
}
