package woowacourse.shopping.ui.cart.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.ui.cart.CartItemsView
import woowacourse.shopping.ui.cart.uistate.CartItemUIState
import java.time.LocalDateTime

internal class ShowCartItemsTest {

    private lateinit var view: FakeCartItemsView
    private lateinit var cartItemRepository: CartItemRepository
    private val dummyProducts = List(20) { Product(it.toLong(), "url", "name", 10_000) }
    private val dummyCartItems =
        dummyProducts.map { CartItem(it, LocalDateTime.MAX, 1).apply { id = it.id } }

    @BeforeEach
    fun setUp() {
        view = FakeCartItemsView()
        cartItemRepository = mockk()
    }

    @Test
    fun `장바구니 아이템 보여주기가 실행되면 장바구니 아이템 저장소에서 알맞은 페이지의 아이템들을 가져온다`() {
        val pageSize = 5
        val sut = ShowCartItems(view, cartItemRepository, pageSize)
        val currentPage = 2
        every { cartItemRepository.findAllOrderByAddedTime(any(), any()) } returns emptyList()

        sut.invoke(currentPage, dummyCartItems.slice(5 until 10).toSet(), true)

        verify { cartItemRepository.findAllOrderByAddedTime(pageSize, 5) }
    }

    @Test
    fun `장바구니 아이템 보여주기가 실행되면 장바구니 아이템 저장소에서 알맞은 페이지의 아이템을 가져온 후 UI 상태로 변경한 것을 뷰에 설정한다`() {
        val pageSize = 5
        val sut = ShowCartItems(view, cartItemRepository, pageSize)
        val currentPage = 2
        every {
            cartItemRepository.findAllOrderByAddedTime(pageSize, 5)
        } returns dummyCartItems.slice(5 until 10)
        val selectedCartItems = dummyCartItems.slice(5 until 10).toSet()

        sut.invoke(currentPage, selectedCartItems, true)

        verify { cartItemRepository.findAllOrderByAddedTime(pageSize, 5) }
        assertThat(view.cartItems)
            .isEqualTo(dummyCartItems.slice(5 until 10).map { CartItemUIState.create(it, true) })
    }

    class FakeCartItemsView : CartItemsView {
        var cartItems: List<CartItemUIState>? = null
        var initializedScroll: Boolean? = null

        override fun setCartItems(cartItems: List<CartItemUIState>, initScroll: Boolean) {
            this.cartItems = cartItems
            this.initializedScroll = initScroll
        }

    }
}