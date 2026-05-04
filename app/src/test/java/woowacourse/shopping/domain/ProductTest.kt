package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ProductTest {
    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `각 상품마다 다른 ID를 가진다`() {
        val product1 =
            Product(
                imageUrl = "",
                productName = "동원 스위트콘",
                price = Price(99800),
            )

        val product2 =
            Product(
                imageUrl = "",
                productName = "동원 참치",
                price = Price(10000),
            )

        assertThat(product1.productId).isNotEqualTo(product2.productId)
    }
}
