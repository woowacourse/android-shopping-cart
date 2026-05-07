package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ProductWithQuantityTest {
    val product =
        Product(
            productId = Uuid.random(),
            imageUrl = "",
            productName = "동원 스위트콘",
            price = Price(99800),
        )

    @Test
    fun `선택한 상품의 수량을 늘릴 수 있다`() {
        val productWithQuantity = ProductWithQuantity(product, 1)

        val newProductWithQuantity = productWithQuantity.increaseQuantity(
            quantityToAdd = 1
        )

        assertThat(newProductWithQuantity.quantity).isEqualTo(2)
    }

    @Test
    fun `선택한 상품의 수량을 줄일 수 있다`() {
        val productWithQuantity = ProductWithQuantity(product, 1)

        val newProductWithQuantity = productWithQuantity.decreaseQuantity(
            quantityToRemove = 1
        )

        assertThat(newProductWithQuantity.quantity).isEqualTo(0)
    }
}
