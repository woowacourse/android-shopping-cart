package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ShoppingCartTest {
    private val product = Product(1, "", "아메리카노", 2500)

    @Test
    fun `장바구니에 상품이 포함되어 있는지 확인한다`() {
        val cart = ShoppingCart() + product
        assertTrue(product in cart)
    }

    @Test
    fun `장바구니에 상품이 없다면 상품을 추가한다`() {
        val cart = ShoppingCart() + product
        assertTrue(product in cart)
        assertEquals(1, cart.cartProducts.size)
        assertEquals(1, cart.cartProducts.first().quantity)
    }

    @Test
    fun `장바구니에 상품이 있다면 해당 상품의 수량을 증가한다`() {
        val cart1 = ShoppingCart() + product
        val cart2 = cart1 + product

        assertEquals(1, cart2.cartProducts.size)

        val cartProduct = cart2.cartProducts.first()
        assertEquals(product, cartProduct.product)
        assertEquals(2, cartProduct.quantity)
    }

    @Test
    fun `상품 수량이 2개일 때 하나 제거하면 수량이 1이 된다`() {
        val cart = ShoppingCart() + product + product
        val updatedCart = cart - product

        val cartProduct = updatedCart.cartProducts.first { it.product == product }
        assertEquals(1, cartProduct.quantity)
        assertTrue(product in updatedCart)
    }

    @Test
    fun `상품 수량이 1개일 때 제거하면 장바구니에서 삭제된다`() {
        val cart = ShoppingCart() + product
        val updatedCart = cart - product

        assertFalse(product in updatedCart)
    }

    @Test
    fun `존재하지 않는 상품을 제거하려 하면 예외가 발생한다`() {
        val cart = ShoppingCart()
        assertThrows(IllegalArgumentException::class.java) {
            cart - product
        }
    }
}
