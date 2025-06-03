package woowacourse.shopping.domain.presentation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.fixture.dummyProductUiModelFixture
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.FakeCartRepository
import woowacourse.shopping.domain.util.InstantTaskExecutorExtension
import woowacourse.shopping.domain.util.getOrAwaitValue
import woowacourse.shopping.presentation.model.toCartItem
import woowacourse.shopping.presentation.view.cart.CartViewModel

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var cartRepository: FakeCartRepository
    private lateinit var viewModel: CartViewModel
    private lateinit var dummyCartItemList: List<CartItem>

    @BeforeEach
    fun setUp() {
        cartRepository = FakeCartRepository()

        dummyCartItemList = dummyProductUiModelFixture.map { it.toCartItem() }
        cartRepository.addTestCartItems(dummyCartItemList)

        viewModel = CartViewModel(cartRepository)
    }

    @Test
    fun `fetchShoppingCart를 호출하면 장바구니 목록이 LiveData에 반영된다`() {
        viewModel.fetchShoppingCart(isNextPage = false)

        val result = viewModel.products.getOrAwaitValue()
        assertThat(result).isEqualTo(dummyCartItemList)
    }

    @Test
    fun `deleteProduct를 호출하면 deleteState에 해당 상품 ID가 전달된다`() {
        val item = dummyCartItemList[0]

        viewModel.deleteProduct(item)

        val result = viewModel.deleteState.getOrAwaitValue()
        assertThat(result).isEqualTo(item.product.id)
    }

    @Test
    fun `increaseAmount를 호출하면 itemUpdateEvent에 변경된 상품이 전달된다`() {
        val item = dummyCartItemList.first()

        viewModel.increaseAmount(item.product.id)

        val result = viewModel.itemUpdateEvent.getOrAwaitValue()
        assertThat(result.amount).isEqualTo(item.amount + 1)
    }

    @Test
    fun `decreaseAmount를 호출하면 itemUpdateEvent에 변경된 상품이 전달된다`() {
        val item = dummyCartItemList.first()
        viewModel.increaseAmount(item.product.id)

        viewModel.decreaseAmount(item.product.id)

        val result = viewModel.itemUpdateEvent.getOrAwaitValue()
        assertThat(result.amount).isEqualTo(1)
    }
}
