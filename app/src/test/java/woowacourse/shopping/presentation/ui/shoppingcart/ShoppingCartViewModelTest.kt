package woowacourse.shopping.presentation.ui.shoppingcart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.DummyData
import woowacourse.shopping.data.repsoitory.DummyShoppingCart
import woowacourse.shopping.domain.model.PagingOrder
import woowacourse.shopping.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class ShoppingCartViewModelTest {
    private lateinit var viewModel: ShoppingCartViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ShoppingCartViewModel(DummyShoppingCart)
    }

    @Test
    fun `첫 번째 페이지에 장바구니를 불러온다`() {
        // given
        val dummyPagingOrder = DummyShoppingCart.getPagingOrder(0, 5).getOrThrow()

        // when
        val actual = viewModel.uiState.getOrAwaitValue()

        // then
        val expected = dummyPagingOrder
        assertThat(actual.pagingOrder).isEqualTo(expected)
    }

    @Test
    fun `주문을 삭제하면 장바구니에 주문이 사라진다`() {
        // given
        val dummyPagingProduct = DummyShoppingCart.getPagingOrder(0, 5).getOrThrow()
        val closeOrder = dummyPagingProduct.orderList.first()

        // when
        viewModel.onClickClose(closeOrder.id)
        val actual = viewModel.uiState.getOrAwaitValue()

        // then
        val expected =
            PagingOrder(
                currentPage = 0,
                orderList =
                    listOf(
                        DummyShoppingCart.order.copy(id = 2, product = DummyData.STUB_PRODUCT_B),
                        DummyShoppingCart.order.copy(id = 3, product = DummyData.STUB_PRODUCT_C),
                        DummyShoppingCart.order.copy(id = 4, product = DummyData.STUB_PRODUCT_A),
                        DummyShoppingCart.order.copy(id = 5, product = DummyData.STUB_PRODUCT_B),
                        DummyShoppingCart.order.copy(id = 6, product = DummyData.STUB_PRODUCT_C),
                    ),
                last = false,
            )
        assertThat(actual.pagingOrder).isEqualTo(expected)
    }

    @Test
    fun `첫 번째 페이지에서 다음 페이지로 넘어가면 다음 페이지 장바구니를 불러온다`() {
        // given & when
        viewModel.onClickNextPage()
        val actual = viewModel.uiState.getOrAwaitValue()

        // then
        val expected =
            PagingOrder(
                currentPage = 1,
                orderList =
                    listOf(
                        DummyShoppingCart.order.copy(id = 6, product = DummyData.STUB_PRODUCT_C),
                        DummyShoppingCart.order.copy(id = 7, product = DummyData.STUB_PRODUCT_A),
                        DummyShoppingCart.order.copy(id = 8, product = DummyData.STUB_PRODUCT_B),
                        DummyShoppingCart.order.copy(id = 9, product = DummyData.STUB_PRODUCT_C),
                        DummyShoppingCart.order.copy(id = 10, product = DummyData.STUB_PRODUCT_A),
                    ),
                last = false,
            )
        assertThat(actual.pagingOrder).isEqualTo(expected)
    }

    @Test
    fun `두 번째 페이지에서 이전 페이지로 넘어가면 다음 페이지 장바구니를 불러온다`() {
        // given & when
        viewModel.onClickNextPage()
        viewModel.onClickPrePage()
        val actual = viewModel.uiState.getOrAwaitValue()

        // then
        val expected =
            PagingOrder(
                currentPage = 0,
                orderList =
                    listOf(
                        DummyShoppingCart.order,
                        DummyShoppingCart.order.copy(id = 2, product = DummyData.STUB_PRODUCT_B),
                        DummyShoppingCart.order.copy(id = 3, product = DummyData.STUB_PRODUCT_C),
                        DummyShoppingCart.order.copy(id = 4, product = DummyData.STUB_PRODUCT_A),
                        DummyShoppingCart.order.copy(id = 5, product = DummyData.STUB_PRODUCT_B),
                    ),
                last = false,
            )
        assertThat(actual.pagingOrder).isEqualTo(expected)
    }
}
