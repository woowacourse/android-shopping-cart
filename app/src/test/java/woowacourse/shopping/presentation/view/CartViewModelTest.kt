package woowacourse.shopping.presentation.view

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.fixture.fakeCartRepository
import woowacourse.shopping.fixture.fakeProductRepository
import woowacourse.shopping.presentation.view.cart.CartViewModel
import woowacourse.shopping.presentation.view.util.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.view.util.getOrAwaitValue

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        viewModel = CartViewModel(fakeCartRepository(), fakeProductRepository)
    }

    @Test
    fun `초기화 시 장바구니 아이템이 로드된다`() {
        val items = viewModel.cartItems.getOrAwaitValue()

        assertAll(
            { assertThat(items).isNotNull },
            { assertThat(items).isNotEmpty },
        )
    }

    @Test
    fun `다음 페이지 요청 시 page 증가 및 아이템 추가된다`() {
        val before = viewModel.cartItems.getOrAwaitValue()

        viewModel.fetchCartItems(isNextPage = true)
        val after = viewModel.cartItems.getOrAwaitValue()

        assertThat(after.size).isGreaterThanOrEqualTo(before.size)
        assertThat(viewModel.page.getOrAwaitValue()).isEqualTo(2)
    }

    @Test
    fun `삭제 요청 시 deleteEvent가 발생한다`() {
        val items = viewModel.cartItems.getOrAwaitValue()
        val target = items.first()

        viewModel.deleteCartItem(target)
        val deleteEventId = viewModel.deleteEvent.getValue()

        assertThat(deleteEventId).isEqualTo(target.id)
    }

    @Test
    fun `deleteEvent 발생 시 refreshEvent가 발생한다`() {
        viewModel.fetchCartItems(isNextPage = false, isRefresh = true)

        val refreshItems = viewModel.refreshEvent.getValue()

        assertThat(refreshItems).isNotEmpty
    }

    @Test
    fun `호출 가능한 다음 목록이 있다면 hasMore가 True로 설정된다`() {
        val hasMore = viewModel.hasMore.getOrAwaitValue()

        assertThat(hasMore).isTrue()
    }
}
