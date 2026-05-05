package woowacourse.shopping.ui.cart

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.ui.cart.stateholder.CartStateHolder
import woowacourse.shopping.ui.state.ProductUiModel

class CartStateHolderTest {
    private fun createMockItems(size: Int): List<ProductUiModel> = (1..size).map {
        ProductUiModel(title = "상품$it", price = "1,000원", imageUrl = "", id = "$it")
    }

    @Test
    fun `빈 리스트일때 페이지네이션은 빈리스트를 반환한다`() {
        val holder = CartStateHolder(emptyList())

        assertTrue(holder.cartItems.isEmpty())
        assertEquals(1, holder.page)

        assertTrue(holder.isEndPage())
    }

    @Test
    fun `CartItem 11개를 5개씩 페이징할때 3페이지는 1개만 보여주고 끝페이지다`() {
        val items = createMockItems(11)
        val holder = CartStateHolder(items)

        holder.onRightClick() // 2페이지
        holder.onRightClick() // 3페이지

        assertEquals(3, holder.page) // 3페이지
        assertEquals(1, holder.cartItems.size) // 1개
        assertEquals("11", holder.cartItems.first().id) // 11번째 item
        assertTrue(holder.isEndPage()) // 페이지 끝
    }

    @Test
    fun `마지막 페이지의 마지막 아이템 삭제시 페이지가 감소한다`() {
        val items = createMockItems(6)
        val holder = CartStateHolder(items)

        holder.onRightClick() // 2페이지
        assertEquals(2, holder.page) // 2페이지
        assertEquals(1, holder.cartItems.size) // 1개

        holder.deleteCartItem("6")

        assertEquals(1, holder.page) // 1페이지
        assertEquals(5, holder.cartItems.size) // 5개
    }

    @Test
    fun `페이지에서 왼쪽 버튼을 누를수 있다면 페이지가 변경된다`() {
        val items = createMockItems(6)
        val holder = CartStateHolder(items)

        holder.onRightClick() // 첫 페이지는 1이므로 한번 오른쪽 2페이지
        holder.onLeftClick() // 1페이지

        assertEquals(1, holder.page)
    }

    @Test
    fun `1페이지에서 왼쪽 버튼을 누르면 페이지가 변경되지 않는다`() {
        val items = createMockItems(5)
        val holder = CartStateHolder(items)

        holder.onLeftClick()

        assertEquals(1, holder.page)
    }

    @Test
    fun `페이지에서 오른쪽 버튼을 누를수 있다면 페이지가 변경된다`() {
        val items = createMockItems(6)
        val holder = CartStateHolder(items)

        holder.onRightClick() // 첫 페이지는 1이므로 한번 오른쪽 2페이지

        assertEquals(2, holder.page)
    }

    @Test
    fun `마지막 페이지에서 오른쪽 버튼을 누르면 페이지가 변경되지 않는다`() {
        val items = createMockItems(5)
        val holder = CartStateHolder(items)

        holder.onRightClick()
        assertEquals(1, holder.page)
    }
}
