package woowacourse.shopping

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.Product

class CartProductsTest {
    @Test
    fun `상품을 추가하면 해당 상품이 포함된다`() {
        val newProduct =
            Product(
                imageUri = "image",
                name = "twohander",
                price = 10000,
            )

        val cartProducts2 = CartProducts().addQuantityOfCartProduct(newProduct)

        assertTrue(cartProducts2.items.first().product == newProduct)
    }

    @Test
    fun `상품을 제거하면 해당 상품이 포함되지 않는다`() {
        val newProduct =
            CartProduct(
                product = Product(
                    imageUri = "image",
                    name = "twohander",
                    price = 10000,
                )
            )
        val cartProducts1 = CartProducts(products = listOf(newProduct))
        val targetId = newProduct.product.productId
        val cartProducts2 = cartProducts1.remove(targetId)

        assertTrue(cartProducts2.items.contains(newProduct).not())
    }

    @Test
    fun `상품을 id로 검색 한다`() {
        val newProduct =
            CartProduct(
                product = Product(
                    imageUri = "image",
                    name = "twohander",
                    price = 10000,
                )
            )
        val cartProducts1 = CartProducts(products = listOf(newProduct))
        val targetId = newProduct.product.productId

        val foundProduct = cartProducts1.findSameProduct(targetId)

        assertEquals(newProduct, foundProduct)
    }

    @Test
    fun `id 검색에 실패했다면 null을 반환한다`() {
        val newProduct = CartProduct(
            product = Product(
                imageUri = "image",
                name = "twohander",
                price = 10000,
            )
        )
        val newProduct2 =
            Product(
                imageUri = "image",
                name = "samuel",
                price = 50,
            )
        val cartProducts1 = CartProducts(products = listOf(newProduct))
        val targetId = newProduct2.productId

        val foundProduct = cartProducts1.findSameProduct(targetId)

        assertEquals(null, foundProduct)
    }
}
