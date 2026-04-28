package woowacourse.shopping

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.Product

class CartTest {
    @Test
    fun `사용자가 선택한 상품을 추가할 수 있다`() {
        val cart =
            Cart(CartProducts(listOf()))

        val newProduct = Product(imageUri = "image", name = "TwoHander", price = 10000)
        val newCart = cart.addProduct(newProduct)

        assertTrue(newCart.cartProducts.products.contains(newProduct))
    }

    @Test
    fun `사용자가 선택한 상품을 제거할 수 있다`() {
        val cart =
            Cart(CartProducts(listOf(Product(imageUri = "image", name = "twohander", price = 10000))))

        val newProduct = Product(imageUri = "image", name = "twohander", price = 10000)
        val newCart = cart.removeProduct(newProduct.uuid)

        assertTrue(newCart.cartProducts.products.contains(newProduct).not())
    }
}
