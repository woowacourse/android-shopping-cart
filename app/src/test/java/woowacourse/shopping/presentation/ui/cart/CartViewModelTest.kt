package woowacourse.shopping.presentation.ui.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.ui.getOrAwaitValue
import woowacourse.shopping.presentation.ui.testCartItem0
import woowacourse.shopping.presentation.ui.testCartItem1
import woowacourse.shopping.presentation.ui.testCartItem2
import woowacourse.shopping.presentation.ui.testCartItem3
import woowacourse.shopping.presentation.ui.testCartItem4
import woowacourse.shopping.presentation.ui.testCartItem5

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    @Test
    fun `장바구니에 담긴 상품이 비었을 때를 확인할 수 있다`() {
        // given & when
        val cartRepository = FakeCartRepositoryImpl(emptyList<CartItem>().toMutableList())
        val viewModel = CartViewModel(cartRepository)
        val state = viewModel.cartItemsState.getOrAwaitValue()

        // then
        assertThat(state).isEqualTo(UIState.Empty)
    }

    @Test
    fun `장바구니에 담긴 상품을 확인할 수 있다`() {
        // given
        val cartRepository = FakeCartRepositoryImpl(List(1) { testCartItem0 }.toMutableList())
        val viewModel = CartViewModel(cartRepository)

        // then
        val state = viewModel.cartItemsState.getOrAwaitValue()
        assertThat(state).isEqualTo(UIState.Success(listOf(testCartItem0)))
    }

    @Test
    fun `장바구니에 담긴 상품이 5개 미만일 때 페이지 컨트롤이 보이지 않는다`() {
        // given
        val cartRepository = FakeCartRepositoryImpl(List(4) { testCartItem0 }.toMutableList())
        val viewModel = CartViewModel(cartRepository)

        // then
        val isVisible = viewModel.isPageControlVisible.getOrAwaitValue()
        assertThat(isVisible).isFalse()
    }

    @Test
    fun `장바구니에 담긴 상품이 5개 초과일 때 페이지 컨트롤이 보인다`() {
        // given
        val cartRepository = FakeCartRepositoryImpl(List(6) { testCartItem0 }.toMutableList())
        val viewModel = CartViewModel(cartRepository)

        // then
        val isVisible = viewModel.isPageControlVisible.getOrAwaitValue()
        assertThat(isVisible).isTrue()
    }

    @Test
    fun `아이템이 6개 이상이면 다음 페이지로 이동할 수 있다`() {
        // given
        val cartRepository = FakeCartRepositoryImpl(List(6) { testCartItem0 }.toMutableList())
        val viewModel = CartViewModel(cartRepository)

        // when
        viewModel.loadNextPage()

        // then
        val currentPage = viewModel.currentPage.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(1)
    }

    @Test
    fun `첫 페이지에서 이전 페이지로 이동할 수 없다`() {
        // given
        val cartRepository = FakeCartRepositoryImpl(List(6) { testCartItem0 }.toMutableList())
        val viewModel = CartViewModel(cartRepository)
        // when
        viewModel.loadPreviousPage()

        // then
        val currentPage = viewModel.currentPage.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(0)
    }

    // 삭제
    @Test
    fun `아이템을 삭제할 수 있다`() {
        // given
        val cartRepository = FakeCartRepositoryImpl(List(1) { testCartItem0 }.toMutableList())
        val viewModel = CartViewModel(cartRepository)

        // when
        viewModel.deleteItem(0)

        // then
        val state = viewModel.cartItemsState.getOrAwaitValue()
        assertThat(state).isEqualTo(UIState.Empty)
    }

    @Test
    fun `아이템을 삭제하고 다음 페이지로 이동할 수 있다`() {
        // given
        val cartRepository = FakeCartRepositoryImpl(List(7) { testCartItem0 }.toMutableList())
        val viewModel = CartViewModel(cartRepository)

        // when
        viewModel.deleteItem(1)
        viewModel.loadNextPage()

        // then
        val currentPage = viewModel.currentPage.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(1)
    }

    @Test
    fun `아이템을 삭제하고 이전 페이지로 이동할 수 있다`() {
        // given
        val cartRepository = FakeCartRepositoryImpl(List(6) { testCartItem0 }.toMutableList())
        val viewModel = CartViewModel(cartRepository)

        // when
        viewModel.deleteItem(0)
        viewModel.loadPreviousPage()

        // then
        val currentPage = viewModel.currentPage.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(0)
    }

    @Test
    fun `아이템이 6개일때 삭제하면 첫번째 페이지로 넘어가고 페이지 컨트롤이 보이지 않는다`() {
        // given
        val list = listOf<CartItem>(testCartItem0, testCartItem1, testCartItem2, testCartItem3, testCartItem4, testCartItem5)
        val cartRepository = FakeCartRepositoryImpl(list.toMutableList())
        val viewModel = CartViewModel(cartRepository)

        // when
        viewModel.deleteItem(testCartItem0.id)

        // then
        val currentPage = viewModel.currentPage.getOrAwaitValue()
        val isVisible = viewModel.isPageControlVisible.getOrAwaitValue()
        val size = viewModel.totalItemSize.getOrAwaitValue()
        assertThat(size).isEqualTo(5)
        assertThat(currentPage).isEqualTo(0)
        assertThat(isVisible).isFalse()
    }
}
