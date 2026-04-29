package woowacourse.shopping.domain.model.product

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.Price

class ProductItemsTest {
    private val product = Product(
        id = "1",
        imageUrl = "",
        productTitle = ProductTitle("콜라"),
        price = Price(1000),
    )

    @Test
    fun `동일한 상품이 목록에 중복으로 추가되지 않는다`() {
        val productItems = ProductItems(listOf(product, product))
        assertEquals(1, productItems.items.size)
    }
}
