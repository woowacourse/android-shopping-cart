package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class CartProductTest {
    @Test
    fun `개수는 0 이상이라면 예외가 발생한다`() {
        assertThrows <IllegalArgumentException> {
            CartProduct(product = Product(imageUri = "image", name = "name", price = 10000), amount = -1)
        }
    }

    @Test
    fun `개수가 0 이상이라면 예외가 발생하지 않는다`() {
        assertDoesNotThrow {
            CartProduct(product = Product(imageUri = "image", name = "name", price = 10000), amount = 0)
        }
    }

    @Test
    fun `전체 개수와 가격을 곱해 해당 아이템의 전체 가격을 계산한다`() {
        val cartProduct = CartProduct(product = Product(imageUri = "image", name = "name", price = 10000), amount = 5)
        val cartProduct2 = CartProduct(product = Product(imageUri = "image", name = "name", price = 10000), amount = 0)

        assertEquals(50000L, cartProduct.calculateTotalPrice())
        assertEquals(0L, cartProduct2.calculateTotalPrice())
    }

    @Test
    fun `수량을 줄일 수 있다`() {
        val cartProduct = CartProduct(product = Product(imageUri = "image", name = "name", price = 10000), amount = 5)
        val updatedCartProduct = cartProduct.decreaseQuantity(2)

        assertEquals(3, updatedCartProduct.amount)
    }

    @Test
    fun `수량을 줄여도 0보다 작아지지 않는다`() {
        val cartProduct = CartProduct(product = Product(imageUri = "image", name = "name", price = 10000), amount = 1)
        val updatedCartProduct = cartProduct.decreaseQuantity(2)

        assertEquals(0, updatedCartProduct.amount)
    }

    @Test
    fun `수량을 늘릴 수 있다`() {
        val cartProduct = CartProduct(product = Product(imageUri = "image", name = "name", price = 10000), amount = 1)
        val updatedCartProduct = cartProduct.addQuantity(2)

        assertEquals(3, updatedCartProduct.amount)
    }
}
