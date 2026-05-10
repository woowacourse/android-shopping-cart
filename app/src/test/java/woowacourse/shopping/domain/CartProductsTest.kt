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

    @Test
    fun `이미 존재하는 상품을 추가하면 수량이 합쳐진다`() {
        val product = Product(imageUri = "uri", name = "name", price = 1000)
        val cartProducts = CartProducts().addQuantityOfCartProduct(product, 1)
        val updatedCartProducts = cartProducts.addQuantityOfCartProduct(product, 2)

        assertEquals(1, updatedCartProducts.uniqueItemCount)
        assertEquals(3, updatedCartProducts.totalQuantity)
    }

    @Test
    fun `상품의 수량을 줄일 수 있다`() {
        val product = Product(imageUri = "uri", name = "name", price = 1000)
        val cartProducts = CartProducts().addQuantityOfCartProduct(product, 5)
        val updatedCartProducts = cartProducts.decreaseQuantityOfCartProduct(product.productId, 2)

        assertEquals(3, updatedCartProducts.findSameProduct(product.productId)?.amount)
    }

    @Test
    fun `상품의 수량을 줄여도 0보다 작아지지 않는다`() {
        val product = Product(imageUri = "uri", name = "name", price = 1000)
        val cartProducts = CartProducts().addQuantityOfCartProduct(product, 1)
        val updatedCartProducts = cartProducts.decreaseQuantityOfCartProduct(product.productId, 2)

        assertEquals(0, updatedCartProducts.findSameProduct(product.productId)?.amount)
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
            amount = 100
        )
        val cartProducts1 = CartProducts(products = listOf(newProduct, newProduct2))

        assertEquals(25000L, cartProducts1.calculateTotalPrice())
    }

    @Test
    fun `빈 장바구니의 전체 금액은 0이다`() {
        val cartProducts = CartProducts()
        assertEquals(0L, cartProducts.calculateTotalPrice())
    }

    @Test
    fun `장바구니 금액의 합이 Int 범위를 넘어가도 정상적으로 계산한다`() {
        val product = Product(imageUri = "uri", name = "name", price = 100_000_000)
        val cartProducts = CartProducts(
            products = listOf(
                CartProduct(product = product, amount = 22)
            )
        )
        assertEquals(2_200_000_000L, cartProducts.calculateTotalPrice())
    }

    @Test
    fun `수량을 줄일 대상 상품이 포함되어 있지 않다면 스스로를 반환한다`() {
        val product = Product(imageUri = "uri", name = "name", price = 1000)
        val cartProducts = CartProducts()
        val newCartProducts = cartProducts.decreaseQuantityOfCartProduct(product.productId, 1)

        assertEquals(cartProducts, newCartProducts)
    }
}
