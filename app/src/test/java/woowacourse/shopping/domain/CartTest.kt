package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CartTest {
    @Test
    fun `사용자가 선택한 상품을 추가할 수 있다`() {
        val cart =
            Cart(CartProducts(listOf()))

        val newProduct = CartProduct(product = Product(imageUri = "image", name = "TwoHander", price = 10000))
        val newCart = cart.addProduct(newProduct.product)

        assertTrue(newCart.cartProducts.items.first().product == newProduct.product)
    }

    @Test
    fun `사용자가 선택한 상품을 제거할 수 있다`() {
        val newProduct = CartProduct(product = Product(imageUri = "image", name = "twohander", price = 10000))
        val cart =
            Cart(CartProducts(listOf(newProduct)))

        val newCart = cart.removeProduct(newProduct.product.productId)

        assertTrue(
            newCart.cartProducts.items
                .contains(newProduct)
                .not()
        )
    }

    @Test
    fun `사용자가 새로운 상품을 원하는 개수만큼 추가한다`() {
        val cart = Cart(CartProducts(listOf()))
        val newProduct = CartProduct(product = Product(imageUri = "image", name = "twohander", price = 10000))
        val newCart = cart.addProduct(newProduct.product, 5)

        assertTrue(newCart.cartProducts.items.first().amount == 5)
    }

    @Test
    fun `겹치는 상품을 추가하면 전체 개수가 증가한다`() {
        val cart = Cart(CartProducts(listOf(CartProduct(product = Product(imageUri = "image", name = "Samuel", price = 8000)))))
        val newProduct = Product(imageUri = "image", name = "twohander", price = 10000)
        val newCart = cart.addProduct(newProduct, 5)

        assertEquals(6, newCart.getTotalQuantity())
    }

    @Test
    fun `장바구니에 담긴 전체 금액을 계산한다`() {
        val newProduct = CartProduct(
            product = Product(
                imageUri = "image",
                name = "twohander",
                price = 10000,
            ),
            amount = 2
        )
        val newProduct2 = CartProduct(
            product = Product(
                imageUri = "image",
                name = "samuel",
                price = 50,
            ),
            amount = 1000
        )
        val cartProducts1 = CartProducts(products = listOf(newProduct, newProduct2))

        assertEquals(70000, cartProducts1.calculateTotalPrice())
    }
}
