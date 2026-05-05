package woowacourse.shopping

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products

class CartTest {
    @Test
    fun `사용자가 선택한 상품을 추가할 수 있다`() {
        val cart =
            Cart(Products(listOf()))

        val newProduct = Product(imageUri = "image", name = "TwoHander", price = 10000)
        val newCart = cart.addProduct(newProduct)

        assertTrue(newCart.products.products.contains(newProduct))
    }

    @Test
    fun `사용자가 선택한 상품을 제거할 수 있다`() {
        val cart = Cart()
        val newProduct = Product(imageUri = "image", name = "twohander", price = 10000)
        val productAddedCart = cart.addProduct(newProduct)
        val productRemovedCart = productAddedCart.removeProduct(newProduct.uuid)

        assertTrue(
            productRemovedCart.products.products
                .contains(newProduct)
                .not(),
        )
    }
}
