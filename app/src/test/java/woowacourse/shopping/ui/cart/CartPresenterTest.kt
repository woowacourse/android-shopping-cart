package woowacourse.shopping.ui.cart

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.ui.cart.presenter.CartPresenter
import woowacourse.shopping.ui.cart.uistate.CartItemUIState
import java.time.LocalDateTime

internal class CartPresenterTest {

    private lateinit var view: FakeView
    private lateinit var cartItemRepository: CartItemRepository
    private lateinit var sut: CartPresenter

    @BeforeEach
    fun setUp() {
        view = FakeView()
        cartItemRepository = mockk()
        sut = CartPresenter(view, cartItemRepository)
    }

    @Test
    fun `현재 페이지를 회복하면 현재 페이지를 변경 후 그에 맞는 장바구니 아이템들, 페이지 UI, 모두 선택 UI를 설정하고 스크롤을 초기화한다`() {
        val products = (1..5).map {
            Product(it.toLong(), "url", "name", 10000)
        }
        val cartItems = products.map {
            CartItem(it, LocalDateTime.now(), 1).apply { id = it.id }
        }
        val page = 2
        every { cartItemRepository.findAllOrderByAddedTime(5, 5) } returns cartItems
        val cartItemUIStates = cartItems.map { CartItemUIState.create(it, false) }
        every { cartItemRepository.countAll() } returns 10

        sut.restoreCurrentPage(page)

        assertThat(view.cartItems).isEqualTo(cartItemUIStates)
        assertThat(view.initializedScroll).isTrue
        assertThat(view.isVisiblePageUI).isTrue
        assertThat(view.canRequestNextPage).isFalse
        assertThat(view.canRequestPreviousPage).isTrue
        assertThat(view.pageUI).isEqualTo(page)
        assertThat(view.isAllSelected).isFalse
    }

    @Test
    fun `선택한 장바구니 아이템을 회복하면 선택한 장바구니 아이템을 변경 후 그에 맞는 장바구니 아이템들, 모두 선택 UI, 주문 UI를 설정한다`() {
        val products = (1..5).map {
            Product(it.toLong(), "url", "name", 10000)
        }
        val cartItems = products.map {
            CartItem(it, LocalDateTime.now(), 1).apply { id = it.id }
        }
        val cartItemIds = listOf<Long>(1, 2, 3, 4, 5)
        every { cartItemRepository.findAllByIds(cartItemIds) } returns cartItems
        every { cartItemRepository.findAllOrderByAddedTime(any(), any()) } returns cartItems

        sut.restoreSelectedCartItems(cartItemIds)

        val cartItemUIStates = cartItems.map { CartItemUIState.create(it, true) }
        assertThat(view.cartItems).isEqualTo(cartItemUIStates)
        assertThat(view.isAllSelected).isTrue
        assertThat(view.orderPriceUI).isEqualTo(50000)
        assertThat(view.orderCountUI).isEqualTo(5)
    }

    @Test
    fun `다음 페이지의 장바구니 아이템을 로드하면 현재 페이지를 1 증가시키고 그에 맞는 장바구니 아이템들, 페이지 UI, 모두 선택 UI를 설정한다`() {
        val products = (1..5).map {
            Product(it.toLong(), "url", "name", 10000)
        }
        val cartItems = products.map {
            CartItem(it, LocalDateTime.now(), 1).apply { id = it.id }
        }
        every { cartItemRepository.findAllOrderByAddedTime(5, 0) } returns cartItems
        every { cartItemRepository.countAll() } returns 5

        sut.onLoadCartItemsOfNextPage()

        val cartItemUIStates = cartItems.map { CartItemUIState.create(it, false) }
        assertThat(sut.currentPage).isEqualTo(1)
        assertThat(view.cartItems).isEqualTo(cartItemUIStates)
        assertThat(view.isVisiblePageUI).isFalse
        assertThat(view.isAllSelected).isFalse
    }

    @Test
    fun `마지막 페이지의 장바구니 아이템을 로드하면 현재 페이지를 마지막 페이지로 변경하고 그에 맞는 장바구니 아이템들, 페이지 UI, 모두 선택 UI를 설정한다`() {
        val products = (1..5).map {
            Product(it.toLong(), "url", "name", 10000)
        }
        val cartItems = products.map {
            CartItem(it, LocalDateTime.now(), 1).apply { id = it.id }
        }
        every { cartItemRepository.findAllOrderByAddedTime(5, 10) } returns cartItems
        every { cartItemRepository.countAll() } returns 15

        sut.onLoadCartItemsOfLastPage()

        val cartItemUIStates = cartItems.map { CartItemUIState.create(it, false) }
        assertThat(sut.currentPage).isEqualTo(3)
        assertThat(view.cartItems).isEqualTo(cartItemUIStates)
        assertThat(view.initializedScroll).isTrue
        assertThat(view.isVisiblePageUI).isTrue
        assertThat(view.canRequestNextPage).isFalse
        assertThat(view.canRequestPreviousPage).isTrue
        assertThat(view.pageUI).isEqualTo(3)
        assertThat(view.isAllSelected).isFalse
    }

    @Test
    fun `장바구니 아이템을 삭제하면 현재 페이지에 맞는 장바구니 아이템들, 페이지 UI, 모두 선택 UI, 주문 UI를 설정한다`() {
        val products = (1..5).map {
            Product(it.toLong(), "url", "name", 10000)
        }
        val cartItems = products.map {
            CartItem(it, LocalDateTime.now(), 1).apply { id = it.id }
        }
        every { cartItemRepository.deleteById(1L) } just runs
        every { cartItemRepository.findAllOrderByAddedTime(any(), any()) } returns cartItems
        every { cartItemRepository.countAll() } returns 5

        sut.onDeleteCartItem(1L)

        val cartItemUIStates = cartItems.map { CartItemUIState.create(it, false) }
        assertThat(view.cartItems).isEqualTo(cartItemUIStates)
        assertThat(view.isVisiblePageUI).isFalse
        assertThat(view.isAllSelected).isFalse
        assertThat(view.orderPriceUI).isEqualTo(0)
        assertThat(view.orderCountUI).isEqualTo(0)
    }

    @Test
    fun `장바구니 아이템을 선택하면 선택된 장바구니 아이템 목록에 추가하고 그에 맞는 모두 선택 UI, 주문 UI를 설정한다`() {
        val products = (1..1).map {
            Product(it.toLong(), "url", "name", 10000)
        }
        val cartItems = products.map {
            CartItem(it, LocalDateTime.now(), 1).apply { id = it.id }
        }
        every { cartItemRepository.findById(1L) } returns cartItems[0]
        every { cartItemRepository.findAllOrderByAddedTime(any(), any()) } returns cartItems

        sut.onChangeSelectionOfCartItem(1L, true)

        assertThat(sut.selectedCartItemIds).isEqualTo(listOf(1L))
        assertThat(view.isAllSelected).isTrue
        assertThat(view.orderPriceUI).isEqualTo(10000)
        assertThat(view.orderCountUI).isEqualTo(1)
    }

    @Test
    fun `모두 선택하면 현재 페이지의 모든 장바구니 아이템을 선택된 장바구니 아이템 목록에 추가하고 그에 맞는 장바구니 아이템들, 모두 선택 UI, 주문 UI를 설정한다`() {
        val products = (1..5).map {
            Product(it.toLong(), "url", "name", 10000)
        }
        val cartItems = products.map {
            CartItem(it, LocalDateTime.now(), 1).apply { id = it.id }
        }
        every { cartItemRepository.findAllOrderByAddedTime(any(), any()) } returns cartItems

        sut.onChangeSelectionOfAllCartItems(true)

        val cartItemUIStates = cartItems.map { CartItemUIState.create(it, true) }
        assertThat(sut.selectedCartItemIds).isEqualTo(listOf<Long>(1, 2, 3, 4, 5))
        assertThat(view.cartItems).isEqualTo(cartItemUIStates)
        assertThat(view.isAllSelected).isTrue
        assertThat(view.orderPriceUI).isEqualTo(50000)
        assertThat(view.orderCountUI).isEqualTo(5)
    }

    class FakeView : CartContract.View {

        var canRequestPreviousPage: Boolean? = null

        var canRequestNextPage: Boolean? = null

        var pageUI: Int? = null

        var isVisiblePageUI: Boolean? = null

        var cartItems: List<CartItemUIState>? = null

        var initializedScroll: Boolean? = null

        var isAllSelected: Boolean? = null

        var orderPriceUI: Int? = null

        var orderCountUI: Int? = null

        override fun setStateThatCanRequestPreviousPage(canRequest: Boolean) {
            this.canRequestPreviousPage = canRequest
        }

        override fun setStateThatCanRequestNextPage(canRequest: Boolean) {
            this.canRequestNextPage = canRequest
        }

        override fun setPage(page: Int) {
            this.pageUI = page
        }

        override fun setPageUIVisibility(isVisible: Boolean) {
            this.isVisiblePageUI = isVisible
        }

        override fun setCartItems(cartItems: List<CartItemUIState>, initScroll: Boolean) {
            this.cartItems = cartItems
            this.initializedScroll = initScroll
        }

        override fun setStateOfAllSelection(isAllSelected: Boolean) {
            this.isAllSelected = isAllSelected
        }

        override fun setOrderPrice(price: Int) {
            this.orderPriceUI = price
        }

        override fun setOrderCount(count: Int) {
            this.orderCountUI = count
        }

    }
}