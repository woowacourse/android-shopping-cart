package woowacourse.shopping.presentation.ui.shoppingcart

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.repsoitory.DummyData.ORDERS
import woowacourse.shopping.domain.model.OrderList
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
@ExtendWith(MockKExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var viewModel: ShoppingCartViewModel

    @MockK
    private lateinit var repository: ShoppingCartRepository

    private fun initViewModel() {
        viewModel = ShoppingCartViewModel(repository)
    }

    @Test
    fun `첫 번째 페이지에 장바구니를 불러온다`() {
        // given & when
        val orderList = OrderList(ORDERS.subList(0, 5), ORDERS.size)
        every { repository.getPagingOrder(0, 5) } returns
            Result.success(orderList)
        initViewModel()

        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assert(actual.pagingOrder.orders == orderList.orders)
//        assertThat(actual.pagingOrder.orders).isEqualTo(orderList.orders)
    }

    @Test
    fun `주문을 삭제하면 장바구니에 주문이 사라진다`() {
        // given
        val orderList = OrderList(ORDERS.subList(0, 5), ORDERS.size)
        every { repository.getPagingOrder(0, 5) } returns
            Result.success(orderList)
        every { repository.getPagingOrder(0, 5) } returns
            Result.success(orderList.copy(orders = ORDERS.subList(1, 6)))
        every { repository.removeOrder(orderList.orders.first().id) } just runs
        initViewModel()

        // when
        viewModel.onClickClose(orderList.orders.first().id)

        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assertThat(actual.pagingOrder.orders).isEqualTo(
            orderList.copy(
                orders =
                    ORDERS.subList(
                        1,
                        6,
                    ),
            ).orders,
        )

        assert(
            actual.pagingOrder.orders ==
                orderList.copy(orders = ORDERS.subList(1, 6)).orders,
        )
    }

    @Test
    fun `첫 번째 페이지에서 다음 페이지로 넘어가면 다음 페이지 장바구니를 불러온다`() {
        // given
        val firstPageOrderList = OrderList(ORDERS.subList(0, 5), ORDERS.size)
        val secondPageOrderList = OrderList(ORDERS.subList(5, 10), ORDERS.size)

        every { repository.getPagingOrder(0, 5) } returns
            Result.success(firstPageOrderList)
        every { repository.getPagingOrder(1, 5) } returns
            Result.success(secondPageOrderList)
        initViewModel()

        // when
        viewModel.onClickNextPage()

        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assert(actual.pagingOrder.orders == secondPageOrderList.orders)
    }

    @Test
    fun `두 번째 페이지에서 이전 페이지로 넘어가면 다음 페이지 장바구니를 불러온다`() {
        // given
        val firstPageOrderList = OrderList(ORDERS.subList(0, 5), ORDERS.size)
        val secondPageOrderList = OrderList(ORDERS.subList(5, 10), ORDERS.size)

        every { repository.getPagingOrder(0, 5) } returns
            Result.success(firstPageOrderList)
        every { repository.getPagingOrder(1, 5) } returns
            Result.success(secondPageOrderList)
        initViewModel()

        // when
        viewModel.onClickNextPage()
        viewModel.onClickPrePage()

        // then
        val actual = viewModel.uiState.getOrAwaitValue()
        assert(actual.pagingOrder.orders == firstPageOrderList.orders)
    }
}
