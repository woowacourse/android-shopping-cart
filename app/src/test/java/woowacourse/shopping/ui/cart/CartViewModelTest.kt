package woowacourse.shopping.ui.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.data.CartsImpl

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        viewModel = CartViewModel(CartsImpl)
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
        CartsImpl.save(CHAIR)
        CartsImpl.save(CAR)
        CartsImpl.save(UMBRELLA)

        // when
        viewModel.loadCartItems()

        // then
        assertThat(viewModel.cart.getOrAwaitValue()[0].name).isEqualTo("의자")
        assertThat(viewModel.cart.getOrAwaitValue()[1].name).isEqualTo("자동차")
        assertThat(viewModel.cart.getOrAwaitValue()[2].name).isEqualTo("우산")
    }

    @Test
    fun `상품을 지울 수 있어야 한다`() {
        // given
        CartsImpl.save(CHAIR)
        CartsImpl.save(CAR)
        CartsImpl.save(UMBRELLA)

        // when
        viewModel.loadCartItems()
        viewModel.removeCartItem(1)

        // then
        assertThat(viewModel.cart.getOrAwaitValue()[0].name).isEqualTo("의자")
        assertThat(viewModel.cart.getOrAwaitValue()[1].name).isEqualTo("우산")
    }

    companion object {
        private val CHAIR = Product(imageUrl = "", name = "의자", price = 1000)
        private val CAR = Product(imageUrl = "", name = "자동차", price = 100)
        private val UMBRELLA = Product(imageUrl = "", name = "우산", price = 10000)
    }
}
