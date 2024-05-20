package woowacourse.shopping.presentation.ui.shoppingcart

import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.repsoitory.DummyData.ORDERS
import woowacourse.shopping.domain.model.OrderList
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(MockKExtension::class, InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var viewModel: ShoppingCartViewModel

    @MockK
    private lateinit var repository: ShoppingCartRepository

    @BeforeEach
    fun setUp() {
        every { repository.getPagingOrder(0, 5) } returns
            Result.success(OrderList(ORDERS.subList(0, 5), ORDERS.size))
        every { repository.getPagingOrder(1, 5) } returns
            Result.success(OrderList(ORDERS.subList(5, 10), ORDERS.size))

        viewModel = ShoppingCartViewModel(repository)
    }

    @Test
    fun `첫 번째 페이지에 장바구니를 불러온다`() {
        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.pagingOrder.orders).isEqualTo(ORDERS.subList(0, 5))
    }

    @Test
    fun `주문을 삭제하면 장바구니에 주문이 사라진다`() {
        // given
        val orderIdSlot = CapturingSlot<Int>()
        every { repository.removeOrder(capture(orderIdSlot)) } just runs
        viewModel.removeOrder(ORDERS.first().id)

        // then
        assertThat(orderIdSlot.captured).isEqualTo(ORDERS.first().id)
    }

    @Test
    fun `첫 번째 페이지에서 다음 페이지로 넘어가면 다음 페이지 장바구니를 불러온다`() {
        // when
        viewModel.loadNextPage()

        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.pagingOrder.orders).isEqualTo(ORDERS.subList(5, 10))
    }

    @Test
    fun `두 번째 페이지에서 이전 페이지로 넘어가면 다음 페이지 장바구니를 불러온다`() {
        // given
        viewModel.loadNextPage()

        // when
        viewModel.loadPreviousPage()

        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.pagingOrder.orders).isEqualTo(ORDERS.subList(0, 5))
    }
}
