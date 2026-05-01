package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ProductAndCountTest {
    val product =
        Product(
            productId = Uuid.random(),
            imageUrl = "",
            productName = "동원 스위트콘",
            price = Price(99800),
        )

    @Test
    fun `선택한 상품의 수량을 늘릴 수 있다`() {
        val cartItem = CartItem(product, 1)

        val newProductAndCount = cartItem.increaseQuantity()

        assertThat(newProductAndCount.count()).isEqualTo(2)
    }

    @Test
    fun `선택한 상품의 수량을 줄일 수 있다`() {
        val cartItem = CartItem(product, 1)

        val newProductAndCount = cartItem.decreaseQuantity()

        assertThat(newProductAndCount.count()).isEqualTo(0)
    }

    @Test
    fun `상품의 수량은 0보다 작아질 수 없다`() {
        val cartItem = CartItem(product, 0)

        val newProductAndCount = cartItem.decreaseQuantity()

        assertThat(newProductAndCount.count()).isEqualTo(0)
    }
}
