package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CartTest {

    @Test
    fun `quantityOf 는 해당 상품의 수량을 반환한다`() {
        val product = normalProduct("임시")
        val cart = Cart(listOf(CartContent(product, 3)))

        assertEquals(3, cart.quantityOf(product.id))
    }

    @Test
    fun `quantityOf 는 존재하지 않는 상품이면 0을 반환한다`() {
        val cart = Cart(listOf(CartContent(normalProduct("임시"), 1)))

        assertEquals(0, cart.quantityOf("없는상품"))
    }

    @Test
    fun `totalQuantityOf 는 모든 상품 수량의 합을 반환한다`() {
        val cart = Cart(
            listOf(
                CartContent(normalProduct("A"), 2),
                CartContent(normalProduct("B"), 3),
            ),
        )

        assertEquals(5, cart.totalQuantityOf())
    }

    private fun normalProduct(name: String): Product = Product(
        name = name,
        price = Money(1000),
        imageUrl = "",
    )
}
