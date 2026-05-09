package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

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
    fun `상품이 없다면 개수가 0이다`() {
        val cartProducts = CartProducts()
        assertEquals(0, cartProducts.uniqueItemCount)
        assertEquals(0, cartProducts.totalQuantity)
    }

    @Test
    fun `상품의 개수를 가지고 있다`() {
        val cartProducts = CartProducts(
            products = listOf(
                CartProduct(
                    product = Product(
                        imageUri = "uri",
                        name = "name",
                        price = 10000,
                    ),
                    amount = 1,
                )
            )
        )
        assertEquals(1, cartProducts.uniqueItemCount)
        assertEquals(1, cartProducts.totalQuantity)
    }

    @Test
    fun `상품 종류가 여러 종류를 가지면 가진 종류의 개수만큼 계산한다`() {
        val cartProducts = CartProducts(
            products = listOf(
                CartProduct(
                    product = Product(
                        imageUri = "uri",
                        name = "name",
                        price = 10000,
                    ),
                    amount = 5,
                ),
                CartProduct(
                    product = Product(
                        imageUri = "uri",
                        name = "Samuel",
                        price = 10000,
                    ),
                    amount = 1,
                )
            )
        )
        assertEquals(2, cartProducts.uniqueItemCount)
    }

    @Test
    fun `상품의 전체 개수를 계산한다`() {
        val cartProducts = CartProducts(
            products = listOf(
                CartProduct(
                    product = Product(
                        imageUri = "uri",
                        name = "name",
                        price = 10000,
                    ),
                    amount = 5,
                ),
                CartProduct(
                    product = Product(
                        imageUri = "uri",
                        name = "Samuel",
                        price = 10000,
                    ),
                    amount = 5,
                )
            )
        )
        assertEquals(10, cartProducts.totalQuantity)
    }

    @Test
    fun `지울 대상 상품이 포함되어 있지 않다면 스스로를 반환한다`() {
        val deleteProduct = Product(
            imageUri = "uri",
            name = "deletingProduct",
            price = 1000
        )

        val cartProducts = CartProducts(
            products = listOf(
                CartProduct(
                    product = Product(
                        imageUri = "uri",
                        name = "existingProduct",
                        price = 1000,
                    )
                )
            )
        )

        val newCartProducts = cartProducts.remove(deleteProduct.productId)

        assertEquals(cartProducts, newCartProducts)
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
