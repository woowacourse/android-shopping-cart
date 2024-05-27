package woowacourse.shopping.view.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.data.db.cart.CartRepository
import woowacourse.shopping.helper.FakeCartRepositoryImpl
import woowacourse.shopping.helper.InstantTaskExecutorExtension
import woowacourse.shopping.helper.getOrAwaitValue
import woowacourse.shopping.helper.testCartItem0
import woowacourse.shopping.helper.testProduct0
import woowacourse.shopping.view.state.UIState

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private lateinit var cartRepository: CartRepository

    @BeforeEach
    fun setUp() {
        cartRepository = FakeCartRepositoryImpl()
        cartRepository.deleteAll()
    }

    @Test
    fun `장바구니에 담긴 상품이 비었을 때를 확인할 수 있다`() {
        // given
        viewModel = CartViewModel(cartRepository)

        // when
        val state = viewModel.cartUiState.getOrAwaitValue()

        // then
        assertThat(state).isEqualTo(UIState.Empty)
    }

    @Test
    fun `장바구니에 담긴 상품을 확인할 수 있다`() {
        // given
        cartRepository.save(
            product = testProduct0,
            quantity = 1,
        )

        viewModel = CartViewModel(cartRepository)

        // then
        val state = viewModel.cartUiState.getOrAwaitValue()

        assertThat(state).isEqualTo(UIState.Success(listOf(testCartItem0)))
    }

    @Test
    fun `장바구니에 담긴 상품이 5개 미만일 때 페이지 컨트롤이 보이지 않는다`() {
        // given
        repeat(4) {
            cartRepository.save(
                product = testProduct0,
                quantity = 1,
            )
        }

        // when
        viewModel = CartViewModel(cartRepository)

        // then
        val isVisible = viewModel.isPageControlButtonVisible.getOrAwaitValue()
        assertThat(isVisible).isFalse()
    }

    @Test
    fun `장바구니에 담긴 상품이 5개 초과일 때 페이지 컨트롤이 보인다`() {
        // given
        repeat(6) {
            cartRepository.save(
                product = testProduct0,
                quantity = 1,
            )
        }

        // when
        viewModel = CartViewModel(cartRepository)

        // then
        val isVisible = viewModel.isPageControlButtonVisible.getOrAwaitValue()
        assertThat(isVisible).isTrue()
    }

    @Test
    fun `아이템이 6개 이상이면 다음 페이지로 이동할 수 있다`() {
        // given
        repeat(6) {
            cartRepository.save(
                product = testProduct0,
                quantity = 1,
            )
        }
        viewModel = CartViewModel(cartRepository)

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
            cartRepository.save(
                product = testProduct0,
                quantity = 1,
            )
        }
        viewModel = CartViewModel(cartRepository)

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
        cartRepository.save(
            product = testProduct0,
            quantity = 1,
        )
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.deleteItem(0)

        // then
        val state = viewModel.cartUiState.getOrAwaitValue()
        assertThat(state).isEqualTo(UIState.Empty)
    }

    @Test
    fun `아이템을 삭제하고 다음 페이지로 이동할 수 있다`() {
        // given
        repeat(7) {
            cartRepository.save(
                product = testProduct0,
                quantity = 1,
            )
        }
        viewModel = CartViewModel(cartRepository)

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
            cartRepository.save(
                product = testProduct0,
                quantity = 1,
            )
        }
        viewModel = CartViewModel(cartRepository)

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
            cartRepository.save(
                product = testProduct0,
                quantity = 1,
            )
        }
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.deleteItem(0)

        // then
        val currentPage = viewModel.currentPage.getOrAwaitValue()
        val isVisible = viewModel.isPageControlButtonVisible.getOrAwaitValue()
        assertThat(currentPage).isEqualTo(0)
        assertThat(isVisible).isFalse()
    }

    @Test
    fun `상품 클릭 시 상세 화면으로 이동 이벤트가 발생한다`() {
        // given
        cartRepository.save(
            product = testProduct0,
            quantity = 1,
        )
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.onCartItemClick(testProduct0.id)

        // then
        val event = viewModel.navigateToDetail.getOrAwaitValue()
        assertThat(event.getContentIfNotHandled()).isEqualTo(testProduct0.id)
    }

    @Test
    fun `삭제 버튼 클릭 시 아이템이 삭제되고 알림 이벤트가 발생한다`() {
        // given
        cartRepository.save(
            product = testProduct0,
            quantity = 1,
        )
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.onDeleteButtonClick(testProduct0.id)

        // then
        val state = viewModel.cartUiState.getOrAwaitValue()
        val event = viewModel.notifyDeletion.getOrAwaitValue()
        assertThat(state).isEqualTo(UIState.Empty)
        assertThat(event.getContentIfNotHandled()).isEqualTo(true)
    }

    @Test
    fun `뒤로 가기 버튼 클릭 시 뒤로 가기 이벤트가 발생한다`() {
        // given
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.onBackButtonClick()

        // then
        val event = viewModel.isBackButtonClicked.getOrAwaitValue()
        assertThat(event.getContentIfNotHandled()).isEqualTo(true)
    }

    @Test
    fun `수량 증가 버튼 클릭 시 아이템의 수량이 증가한다`() {
        // given
        cartRepository.save(
            product = testProduct0,
            quantity = 1,
        )
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.onQuantityPlusButtonClick(testProduct0.id)

        // then
        val updatedItem = viewModel.updatedCartItem.getOrAwaitValue()
        assertThat(updatedItem.quantity).isEqualTo(2)
    }

    @Test
    fun `수량 감소 버튼 클릭 시 아이템의 수량이 감소한다`() {
        // given
        cartRepository.save(
            product = testProduct0,
            quantity = 2,
        )
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.onQuqntityMinusButtonClick(testProduct0.id)

        // then
        val updatedItem = viewModel.updatedCartItem.getOrAwaitValue()
        assertThat(updatedItem.quantity).isEqualTo(1)
    }

    @Test
    fun `수량 감소 버튼 클릭 시 아이템의 수량이 1 이하로 줄어들지 않는다`() {
        // given
        cartRepository.save(
            product = testProduct0,
            quantity = 1,
        )
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.onQuqntityMinusButtonClick(testProduct0.id)

        // then
        val updatedItem = viewModel.updatedCartItem.getOrAwaitValue()
        assertThat(updatedItem.quantity).isEqualTo(1)
    }
}
