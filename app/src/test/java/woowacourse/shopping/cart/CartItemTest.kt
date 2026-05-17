package woowacourse.shopping.cart

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.domain.cart.Quantity
import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName

class CartItemTest {
    private val product =
        Product(
            id = 1,
            imageUrl = ImageUrl("https://example.com/image.png"),
            name = ProductName("PET보틀-정사각형"),
            price = Price(10_000),
        )

    @Test
    fun `totalPrice는 상품 가격과 수량의 곱이다`() {
        val cartItem = CartItem(product = product, quantity = Quantity(3))

        assertEquals(30_000, cartItem.totalPrice)
    }

    @Test
    fun `같은 상품을 가진 CartItem은 isSameCartItem이 true이다`() {
        val a = CartItem(product = product, quantity = Quantity(1))
        val b = CartItem(product = product, quantity = Quantity(5))

        assertTrue(a.isSameCartItem(b))
    }

    @Test
    fun `productId 정수로 isSameProduct를 비교할 수 있다`() {
        val cartItem = CartItem(product = product, quantity = Quantity(1))

        assertTrue(cartItem.isSameProduct(1))
        assertFalse(cartItem.isSameProduct(2))
    }

    @Test
    fun `increaseQuantity는 수량이 1 증가한 새 CartItem을 반환한다`() {
        val cartItem = CartItem(product = product, quantity = Quantity(2))

        val result = cartItem.increaseQuantity()

        assertEquals(Quantity(3), result.quantity)
        assertEquals(product, result.product)
    }

    @Test
    fun `decreaseQuantity는 수량이 1 감소한 새 CartItem을 반환한다`() {
        val cartItem = CartItem(product = product, quantity = Quantity(2))

        val result = cartItem.decreaseQuantity()

        assertEquals(Quantity(1), result.quantity)
    }
}
