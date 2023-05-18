package woowacourse.shopping.ui.cart

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.ui.cart.uistate.CartItemUIState
import java.time.LocalDateTime

class CartPresenterTest {

    private lateinit var view: CartContract.View
    private lateinit var cartItemRepository: CartItemRepository
    private lateinit var sut: CartPresenter
    private val dummyProduct = Product(1L, "dummy", "dummy", 20_000)
    private val dummyCartItem = CartItem(dummyProduct, LocalDateTime.MAX, 1)

    @BeforeEach
    fun setUp() {
        view = mockk()
        cartItemRepository = mockk()
        sut = CartPresenter(view, cartItemRepository)
    }

    @Test
    fun `현재 페이지를 요청하면 현재 페이지를 반환한다`() {
        val actual = sut.getCurrentPage()

        assertThat(actual).isZero
    }

    @Test
    fun `현재 페이지를 회복하면 현재 페이지가 변하고 그에 맞는 장바구니 아이템과 페이지 관련 UI를 보여준다`() {
        val size = 5
        val dummyCartItems = List(size) { dummyCartItem }
        every { cartItemRepository.findAllOrderByAddedTime(any(), any()) } returns dummyCartItems
        every { view.setCartItems(dummyCartItems.map(CartItemUIState::from)) } just runs
        every { view.setStateThatCanRequestPreviousPage(any()) } just runs
        every { view.setStateThatCanRequestNextPage(any()) } just runs
        every { cartItemRepository.countAll() } returns size
        val currentPage = 2
        every { view.setPage(currentPage) } just runs

        sut.restoreCurrentPage(currentPage)

        verify { view.setCartItems(dummyCartItems.map(CartItemUIState::from)) }
        verify { view.setStateThatCanRequestPreviousPage(any()) }
        verify { view.setStateThatCanRequestNextPage(any()) }
        verify { view.setPage(currentPage) }
    }

    @Test
    fun `다음 페이지의 장바구니 아이템을 로드하면 현재 페이지가 1 증가하고 그에 맞는 장바구니 아이템과 페이지 관련 UI를 보여준다`() {
        val size = 5
        val dummyCartItems = List(size) { dummyCartItem }
        every { cartItemRepository.findAllOrderByAddedTime(any(), any()) } returns dummyCartItems
        every { view.setCartItems(dummyCartItems.map(CartItemUIState::from)) } just runs
        every { view.setStateThatCanRequestPreviousPage(any()) } just runs
        every { view.setStateThatCanRequestNextPage(any()) } just runs
        every { cartItemRepository.countAll() } returns size
        every { view.setPage(1) } just runs

        sut.onLoadCartItemsNextPage()

        assertThat(sut.getCurrentPage()).isEqualTo(1)
        verify { view.setCartItems(dummyCartItems.map(CartItemUIState::from)) }
        verify { view.setStateThatCanRequestPreviousPage(any()) }
        verify { view.setStateThatCanRequestNextPage(any()) }
        verify { view.setPage(1) }
    }

    @Test
    fun `이전 페이지의 장바구니 아이템을 로드하면 현재 페이지가 1 감소하고 그에 맞는 장바구니 아이템과 페이지 관련 UI를 보여준다`() {
        val size = 5
        val dummyCartItems = List(size) { dummyCartItem }
        every { cartItemRepository.findAllOrderByAddedTime(any(), any()) } returns dummyCartItems
        every { view.setCartItems(dummyCartItems.map(CartItemUIState::from)) } just runs
        every { view.setStateThatCanRequestPreviousPage(any()) } just runs
        every { view.setStateThatCanRequestNextPage(any()) } just runs
        every { cartItemRepository.countAll() } returns size
        every { view.setPage(any()) } just runs
        sut.restoreCurrentPage(2)

        sut.onLoadCartItemsPreviousPage()

        assertThat(sut.getCurrentPage()).isEqualTo(1)
        verify { view.setCartItems(dummyCartItems.map(CartItemUIState::from)) }
        verify { view.setStateThatCanRequestPreviousPage(any()) }
        verify { view.setStateThatCanRequestNextPage(any()) }
        verify { view.setPage(1) }
    }

    @Test
    fun `마지막 페이지의 장바구니 아이템을 로드하면 현재 페이지가 마지막 페이지로 바뀌고 그에 맞는 장바구니 아이템과 페이지 관련 UI를 보여준다`() {
        val size = 5
        val dummyCartItems = List(size) { dummyCartItem }
        every { cartItemRepository.findAllOrderByAddedTime(any(), any()) } returns dummyCartItems
        every { view.setCartItems(dummyCartItems.map(CartItemUIState::from)) } just runs
        every { view.setStateThatCanRequestPreviousPage(any()) } just runs
        every { view.setStateThatCanRequestNextPage(any()) } just runs
        every { cartItemRepository.countAll() } returns size
        every { view.setPage(any()) } just runs

        sut.onLoadCartItemsLastPage()

        assertThat(sut.getCurrentPage()).isEqualTo(1)
        verify { view.setCartItems(dummyCartItems.map(CartItemUIState::from)) }
        verify { view.setStateThatCanRequestPreviousPage(any()) }
        verify { view.setStateThatCanRequestNextPage(any()) }
        verify { view.setPage(any()) }
    }

    @Test
    fun `특정 장바구니 아이템을 삭제하면 장바구니 아이템 저장소에서 그 아이템을 삭제 후 그에 맞는 장바구니 아이템과 페이지 관련 UI를 보여준다`() {
        val size = 5
        val dummyCartItems = List(size) { dummyCartItem }
        val productId = 1L
        every { cartItemRepository.deleteByProductId(productId) } just runs
        every { cartItemRepository.findAllOrderByAddedTime(any(), any()) } returns dummyCartItems
        every { view.setCartItems(dummyCartItems.map(CartItemUIState::from)) } just runs
        every { view.setStateThatCanRequestPreviousPage(any()) } just runs
        every { view.setStateThatCanRequestNextPage(any()) } just runs
        every { cartItemRepository.countAll() } returns size
        every { view.setPage(1) } just runs
        sut.onLoadCartItemsNextPage()

        sut.onDeleteCartItem(productId)

        assertThat(sut.getCurrentPage()).isEqualTo(1)
        verify { view.setCartItems(dummyCartItems.map(CartItemUIState::from)) }
        verify { view.setStateThatCanRequestPreviousPage(any()) }
        verify { view.setStateThatCanRequestNextPage(any()) }
        verify { view.setPage(1) }
    }
}