package woowacourse.shopping.ui.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.model.Cart
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductWithQuantity
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.model.data.CartsImpl
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        CartsImpl.deleteAll()
    }

    @Test
    fun `장바구니에 상품을 넣지 않았다면 장바구니가 비어있어야 한다`() {
        // when
        viewModel = CartViewModel(CartsImpl)

        // then
        assertThat(viewModel.cart.getOrAwaitValue().size).isEqualTo(0)
    }

    @Test
    fun `장바구니에 상품을 담으면 장바구니 화면에서 보여야 한다`() {
        // given
        CartsImpl.save(Cart(productWithQuantity = CHAIR))
        CartsImpl.save(Cart(productWithQuantity = CAR))
        CartsImpl.save(Cart(productWithQuantity = UMBRELLA))

        // when
        viewModel = CartViewModel(CartsImpl)

        // then
        assertThat(viewModel.cart.getOrAwaitValue()[0].productWithQuantity.product.name).isEqualTo("의자")
        assertThat(viewModel.cart.getOrAwaitValue()[1].productWithQuantity.product.name).isEqualTo("자동차")
        assertThat(viewModel.cart.getOrAwaitValue()[2].productWithQuantity.product.name).isEqualTo("우산")
    }

    @Test
    fun `상품을 지울 수 있어야 한다`() {
        // given
        CartsImpl.save(Cart(productWithQuantity = CHAIR))
        val id = CartsImpl.save(Cart(productWithQuantity = CAR))
        CartsImpl.save(Cart(productWithQuantity = UMBRELLA))

        // when
        viewModel = CartViewModel(CartsImpl)
        viewModel.removeCartItem(id)

        // then
        assertThat(viewModel.cart.getOrAwaitValue()[0].productWithQuantity.product.name).isEqualTo("의자")
        assertThat(viewModel.cart.getOrAwaitValue()[1].productWithQuantity.product.name).isEqualTo("우산")
    }

    companion object {
        private val CHAIR =
            ProductWithQuantity(
                product = Product(id = 0L, imageUrl = "", name = "의자", price = 1000),
                quantity = Quantity(1),
            )
        private val CAR =
            ProductWithQuantity(
                product = Product(id = 1L, imageUrl = "", name = "자동차", price = 100),
                quantity = Quantity(1),
            )
        private val UMBRELLA =
            ProductWithQuantity(
                product = Product(id = 2L, imageUrl = "", name = "우산", price = 10000),
                quantity = Quantity(1),
            )
    }
}
