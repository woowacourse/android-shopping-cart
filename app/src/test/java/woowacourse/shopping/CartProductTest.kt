package woowacourse.shopping

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.Product

class CartProductTest {
    @Test
    fun `상품을 추가하면 해당 상품이 포함된다`() {
        val cartProducts1 = CartProducts()
        val newProduct = Product(
            imageUri = "image",
            name = "twohander",
            price = 10000
        )
        val cartProducts2 = cartProducts1.add(newProduct)

        assertTrue(cartProducts2.products.contains(newProduct))
    }

    @Test
    fun `상품을 제거하면 해당 상품이 포함되지 않는다`() {
        val newProduct = Product(
            imageUri = "image",
            name = "twohander",
            price = 10000
        )
        val cartProducts1 = CartProducts(products = listOf(newProduct))
        val targetId = newProduct.uuid
        val cartProducts2 = cartProducts1.remove(targetId)

        assertTrue(cartProducts2.products.contains(newProduct).not())
    }
}
