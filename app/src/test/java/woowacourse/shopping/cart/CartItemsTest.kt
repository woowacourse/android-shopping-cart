package woowacourse.shopping.cart

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.cart.CartItems
import woowacourse.shopping.domain.cart.Quantity
import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName

class CartItemsTest {
    private fun product(
        id: Int,
        price: Int = 10_000,
    ): Product =
        Product(
            id = id,
            imageUrl = ImageUrl("https://example.com/$id.png"),
            name = ProductName("상품-$id"),
            price = Price(price),
        )

    @Test
    fun `addProduct - 새 상품을 추가하면 수량 1로 담긴다`() {
        val cartItems = CartItems()

        val result = cartItems.addProduct(product(1))

        assertEquals(1, result.size())
        assertEquals(Quantity(1), result.findQuantity(1))
    }

    @Test
    fun `addProduct - 이미 담긴 상품을 다시 추가하면 수량이 1 증가한다`() {
        val cartItems = CartItems().addProduct(product(1))

        val result = cartItems.addProduct(product(1))

        assertEquals(1, result.size())
        assertEquals(Quantity(2), result.findQuantity(1))
    }

    @Test
    fun `increase - 담긴 상품의 수량이 1 증가한다`() {
        val cartItems = CartItems().addProduct(product(1))

        val result = cartItems.increase(1)

        assertEquals(Quantity(2), result.findQuantity(1))
    }

    @Test
    fun `increase - 담겨있지 않은 상품에 대해서는 변화가 없다`() {
        val cartItems = CartItems().addProduct(product(1))

        val result = cartItems.increase(2)

        assertEquals(1, result.size())
        assertEquals(Quantity(1), result.findQuantity(1))
    }

    @Test
    fun `decrease - 담긴 상품의 수량이 1 감소한다`() {
        val cartItems =
            CartItems()
                .addProduct(product(1))
                .increase(1)

        val result = cartItems.decrease(1)

        assertEquals(Quantity(1), result.findQuantity(1))
    }

    @Test
    fun `decrease - 수량이 1인 상태에서 감소하면 컬렉션에서 제거된다`() {
        val cartItems = CartItems().addProduct(product(1))

        val result = cartItems.decrease(1)

        assertEquals(0, result.size())
        assertFalse(result.contains(1))
    }

    @Test
    fun `remove - 지정한 상품이 컬렉션에서 제거된다`() {
        val cartItems =
            CartItems()
                .addProduct(product(1))
                .addProduct(product(2))

        val result = cartItems.remove(1)

        assertEquals(1, result.size())
        assertTrue(result.contains(2))
        assertFalse(result.contains(1))
    }

    @Test
    fun `findQuantity - 담겨있지 않은 상품은 0을 반환한다`() {
        val cartItems = CartItems()

        assertEquals(Quantity.ZERO, cartItems.findQuantity(1))
    }

    @Test
    fun `totalQuantity - 담긴 모든 상품의 수량 합계를 반환한다`() {
        val cartItems =
            CartItems()
                .addProduct(product(1))
                .addProduct(product(1))
                .addProduct(product(2))

        assertEquals(3, cartItems.totalQuantity)
    }

    @Test
    fun `totalPrice - 담긴 모든 상품의 가격 합계를 반환한다`() {
        val cartItems =
            CartItems()
                .addProduct(product(1, price = 10_000))
                .addProduct(product(1, price = 10_000))
                .addProduct(product(2, price = 5_000))

        assertEquals(25_000, cartItems.totalPrice)
    }
}
