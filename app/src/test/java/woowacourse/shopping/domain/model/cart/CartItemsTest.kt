package woowacourse.shopping.domain.model.cart

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductTitle

class CartItemsTest {
    private val product1 =
        Product(
            id = "1",
            imageUrl = "",
            productTitle = ProductTitle("콜라"),
            price = Price(1000),
        )
    private val product2 =
        Product(
            id = "1",
            imageUrl = "",
            productTitle = ProductTitle("제로 콜라"),
            price = Price(2000),
        )
    private val cartItem1 = CartItem(product1, Quantity(1))
    private val cartItem2 = CartItem(product2, Quantity(1))

    @Test
    fun `이미 담긴 상품을 장바구니에 추가하면 신규 항목이 추가되지 않는다`() {
        val cartItems =
            CartItems()
                .addOrMerge(CartItem(product1, Quantity(1)))
                .addOrMerge(CartItem(product1, Quantity(1)))

        assertEquals(1, cartItems.items.size)
    }

    @Test
    fun `items를 MutableList로 다운캐스팅하고 외부에서 조작하여도 items의 상태를 변경할 수 없다`() {
        val cartItems = CartItems(mutableListOf(cartItem1))

        assertThrows<UnsupportedOperationException> {
            (cartItems.items as MutableList).add(cartItem2)
        }
    }

    @Test
    fun `생성자에 MutableList를 전달하고 외부에서 변경해도 items를 변경할 수 없다`() {
        val mutableList = mutableListOf(cartItem1)
        val cartItems = CartItems(mutableList)

        mutableList.add(cartItem2)

        assertEquals(1, cartItems.items.size)
    }
}
