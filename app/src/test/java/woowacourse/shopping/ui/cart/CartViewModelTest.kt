package woowacourse.shopping.ui.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartsImpl
import woowacourse.shopping.model.data.ProductsImpl
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        ProductsImpl.deleteAll()
        CartsImpl.deleteAll()
    }

    @Test
    fun `장바구니에 상품을 넣지 않았다면 장바구니가 비어있어야 한다`() {
        // when
        viewModel = CartViewModel(ProductsImpl, CartsImpl)

        // then
        assertThat(viewModel.productWithQuantity.getOrAwaitValue().size).isEqualTo(0)
    }

    @Test
    fun `장바구니에 상품을 담으면 장바구니 화면에서 보여야 한다`() {
        // given
        val productId1 = ProductsImpl.save(CHAIR)
        val productId2 = ProductsImpl.save(CAR)
        val productId3 = ProductsImpl.save(UMBRELLA)

        CartsImpl.save(Cart(productId = productId1))
        CartsImpl.save(Cart(productId = productId2))
        CartsImpl.save(Cart(productId = productId3))

        // when
        viewModel = CartViewModel(ProductsImpl, CartsImpl)

        // then
        assertThat(viewModel.productWithQuantity.getOrAwaitValue()[0].product.name).isEqualTo("의자")
        assertThat(viewModel.productWithQuantity.getOrAwaitValue()[1].product.name).isEqualTo("자동차")
        assertThat(viewModel.productWithQuantity.getOrAwaitValue()[2].product.name).isEqualTo("우산")
    }

    @Test
    fun `상품을 지울 수 있어야 한다`() {
        // given
        val productId1 = ProductsImpl.save(CHAIR)
        val productId2 = ProductsImpl.save(CAR)
        val productId3 = ProductsImpl.save(UMBRELLA)

        CartsImpl.save(Cart(productId = productId1))
        CartsImpl.save(Cart(productId = productId2))
        CartsImpl.save(Cart(productId = productId3))

        // when
        viewModel = CartViewModel(ProductsImpl, CartsImpl)
        viewModel.removeCartItem(productId2)

        // then
        assertThat(viewModel.productWithQuantity.getOrAwaitValue()[0].product.name).isEqualTo("의자")
        assertThat(viewModel.productWithQuantity.getOrAwaitValue()[1].product.name).isEqualTo("우산")
    }

    companion object {
        private val CHAIR = Product(id = 0L, imageUrl = "", name = "의자", price = 1000)
        private val CAR = Product(id = 1L, imageUrl = "", name = "자동차", price = 100)
        private val UMBRELLA = Product(id = 2L, imageUrl = "", name = "우산", price = 10000)
    }
}
