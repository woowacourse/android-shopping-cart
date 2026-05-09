package woowacourse.shopping

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.Product

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
}
