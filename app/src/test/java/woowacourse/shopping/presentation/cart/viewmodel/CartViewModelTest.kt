package woowacourse.shopping.presentation.cart.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.fake.FakeCartRepository
import woowacourse.shopping.fake.fakeProduct

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: CartViewModel
    private lateinit var cartRepository: FakeCartRepository

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        val productMap = (1L..10L).associateWith { fakeProduct(it) }
        cartRepository = FakeCartRepository(productMap)
        viewModel =
            CartViewModel(cartRepository = cartRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `장바구니가 비어있다면 currentCartItems는 비어있고, totalCartSize는 0이다`() =
        runTest {
            viewModel.refreshCart()

            val state = viewModel.uiState.value
            assertThat(state.currentCartItems).isEmpty()
            assertThat(state.totalCartSize).isEqualTo(0)
        }

    @Test
    fun `장바구니에 5개 이하의 상품 종류가 있다면 isCanMoveNext는 false이다`() =
        runTest {
            cartRepository.addItem(1L, 1)
            cartRepository.addItem(2L, 10)
            cartRepository.addItem(3L, 5)

            viewModel.refreshCart()
            val state = viewModel.uiState.value

            assertThat(state.isCanMoveNext).isFalse
        }

    @Test
    fun `increase는 카트에 상품을 추가하고 totalCartSize를 갱신한다`() =
        runTest {
            viewModel.increase(1L)

            assertThat(viewModel.uiState.value.totalCartSize).isEqualTo(1)
        }

    @Test
    fun `decrease는 카트의 수량을 감소시킨다`() =
        runTest {
            cartRepository.addItem(1L, 3)

            viewModel.decrease(1L)

            assertThat(cartRepository.getQuantity(1L)).isEqualTo(2)
        }

    @Test
    fun `deleteItem은 성공 시 DeleteSuccess 이벤트를 발생시킨다`() =
        runTest {
            cartRepository.addItem(1L, 1)

            viewModel.deleteItem(1L)

            assertThat(viewModel.uiEvents.first()).isEqualTo(CartEvent.DeleteSuccess)
        }

    @Test
    fun `deleteItem은 없는 상품이면 DeleteNotFound 이벤트를 발생시킨다`() =
        runTest {
            viewModel.deleteItem(999L)

            assertThat(viewModel.uiEvents.first()).isEqualTo(CartEvent.DeleteNotFound)
        }
}
