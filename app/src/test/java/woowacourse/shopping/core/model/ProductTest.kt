package woowacourse.shopping.core.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class ProductTest {
    @Test
    fun `동일한 id를 가진 상품은 동일 상품이다`() {
        val id = UUID.randomUUID()
        val product1 =
            Product(
                id = id,
                name = ProductName("동원 스위트콘"),
                price = Money(5000),
                imageUrl = "dsdsds",
            )
        val product2 =
            Product(
                id = id,
                name = ProductName("동원 스위트콘"),
                price = Money(5000),
                imageUrl = "dsdsds",
            )
        assertThat(product1).isEqualTo(product2)
    }
}
