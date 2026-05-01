package woowacourse.shopping

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.Product

class ProductsTest {
    @Test
    fun `상품을 추가하면 해당 상품이 포함된다`() {
        val products1 = Products()
        val newProduct =
            Product(
                imageUri = "image",
                name = "twohander",
                price = 10000,
            )
        val cartProducts2 = products1.add(newProduct)

        assertTrue(cartProducts2.products.contains(newProduct))
    }

    @Test
    fun `상품을 제거하면 해당 상품이 포함되지 않는다`() {
        val newProduct =
            Product(
                imageUri = "image",
                name = "twohander",
                price = 10000,
            )
        val products1 = Products(products = listOf(newProduct))
        val targetId = newProduct.uuid
        val cartProducts2 = products1.remove(targetId)

        assertTrue(cartProducts2.products.contains(newProduct).not())
    }

    @Test
    fun `상품을 id로 검색 한다`() {
        val newProduct =
            Product(
                imageUri = "image",
                name = "twohander",
                price = 10000,
            )
        val products1 = Products(products = listOf(newProduct))
        val targetId = newProduct.uuid

        val foundProduct = products1.findWithId(targetId)

        assertEquals(newProduct, foundProduct)
    }

    @Test
    fun `id 검색에 실패했다면 null을 반환한다`() {
        val newProduct =
            Product(
                imageUri = "image",
                name = "twohander",
                price = 10000,
            )
        val newProduct2 =
            Product(
                imageUri = "image",
                name = "samuel",
                price = 50,
            )
        val products1 = Products(products = listOf(newProduct))
        val targetId = newProduct2.uuid

        val foundProduct = products1.findWithId(targetId)

        assertEquals(null, foundProduct)
    }
}
