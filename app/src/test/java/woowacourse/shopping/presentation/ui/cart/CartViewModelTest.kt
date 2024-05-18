package woowacourse.shopping.presentation.ui.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.presentation.state.UIState
import woowacourse.shopping.presentation.ui.InstantTaskExecutorExtension
import woowacourse.shopping.presentation.ui.getOrAwaitValue
import woowacourse.shopping.presentation.ui.testCartItem0
import woowacourse.shopping.presentation.ui.testProduct0

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private lateinit var testCartRepository: CartRepository

    @BeforeEach
    fun setUp() {
        testCartRepository = FakeCartRepositoryImpl()
        viewModel = CartViewModel(testCartRepository)
        testCartRepository.deleteAll()
    }

    @Test
    fun `장바구니에 담긴 상품이 비었을 때를 확인할 수 있다`() {
        // given
        testCartRepository.deleteAll()
        viewModel.loadPage(0)

        // when
        val state = viewModel.cartItemsState.getOrAwaitValue()

        // then
        assertThat(state).isEqualTo(UIState.Empty)
    }

    @Test
    fun `장바구니에 담긴 상품을 확인할 수 있다`() {
        // given
        testCartRepository.insert(
            product = testProduct0,
            quantity = 1,
        )

        // then
        val state = viewModel.cartItemsState.getOrAwaitValue()
        assertThat(state).isEqualTo(UIState.Success(listOf(testCartItem0)))
    }

    @Test
    fun `장바구니에 담긴 상품이 5개 미만일 때 페이지 컨트롤이 보이지 않는다`() {
        // given
        repeat(4) {
            testCartRepository.insert(
                product = testProduct0,
                quantity = 1,
            )
        }

        // when
        // viewModel.loadCartItems()

        // then
        val isVisible = viewModel.isPageControlVisible.getOrAwaitValue()
        assertThat(isVisible).isFalse()
    }

    @Test
    fun `장바구니에 담긴 상품이 5개 초과일 때 페이지 컨트롤이 보인다`() {
        // given
        repeat(6) {
            testCartRepository.insert(
                product = testProduct0,
                quantity = 1,
            )
        }

        // when
        viewModel.loadPage(0)

        // then
        val isVisible = viewModel.isPageControlVisible.getOrAwaitValue()
        assertThat(isVisible).isTrue()
    }

    @Test
    fun `아이템이 6개 이상이면 다음 페이지로 이동할 수 있다`() {
        // given
        repeat(6) {
            testCartRepository.insert(
                product = testProduct0,
                quantity = 1,
            )
        }
        // viewModel.loadCartItems()

        // when
        viewModel.loadNextPage()

        // then
        val currentPage = viewModel.currentPage.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(1)
    }

    @Test
    fun `첫 페이지에서 이전 페이지로 이동할 수 없다`() {
        // given
        repeat(6) {
            testCartRepository.insert(
                product = testProduct0,
                quantity = 1,
            )
        }
        // viewModel.loadCartItems()

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
        testCartRepository.insert(
            product = testProduct0,
            quantity = 1,
        )
        // viewModel.loadCartItems()

        // when
        viewModel.deleteItem(0)

        // then
        val state = viewModel.cartItemsState.getOrAwaitValue()
        assertThat(state).isEqualTo(UIState.Empty)
    }

    @Test
    fun `아이템을 삭제하고 다음 페이지로 이동할 수 있다`() {
        // given
        repeat(7) {
            testCartRepository.insert(
                product = testProduct0,
                quantity = 1,
            )
        }
        viewModel.loadPage(0)

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
        repeat(6) {
            testCartRepository.insert(
                product = testProduct0,
                quantity = 1,
            )
        }
        // viewModel.loadCartItems()

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
        repeat(6) {
            testCartRepository.insert(
                product = testProduct0,
                quantity = 1,
            )
        }
        viewModel.loadPage(0)

        // when
        viewModel.deleteItem(0)

        // then
        val currentPage = viewModel.currentPage.getOrAwaitValue()
        val isVisible = viewModel.isPageControlVisible.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(0)
        assertThat(isVisible).isFalse()
    }
}
