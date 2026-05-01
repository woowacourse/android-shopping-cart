package woowacourse.shopping.core.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class ProductTest {
    private val product =
        Product(
            id = UUID.randomUUID().toString(),
            name = ProductName("동원 스위트콘"),
            price = Money(5000),
            imageUrl = "dsdsds",
        )

    @Test
    fun `동일한 id를 가진 상품끼리 비교하면 true를 반환한다`() {
        val id = UUID.randomUUID().toString()
        val product1 =
            Product(
                id = id,
                name = ProductName("동원 스위트콘"),
                price = Money(5000),
                imageUrl = "url0",
            )
        val product2 =
            Product(
                id = id,
                name = ProductName("동원 스위트콘"),
                price = Money(5000),
                imageUrl = "url1",
            )
        assertThat(product1.equals(product2)).isTrue
    }

    @Test
    fun `상품의 id가 다른 상품끼리 비교하면 false를 반환한다`() {
        val id1 = "1"
        val id2 = "2"
        val product1 =
            Product(
                id = id1,
                name = ProductName("동원 스위트콘"),
                price = Money(5000),
                imageUrl = "dsdsds",
            )
        val product2 =
            Product(
                id = id2,
                name = ProductName("동원 스위트콘"),
                price = Money(5000),
                imageUrl = "dsdsds",
            )
        assertThat(product1.equals(product2)).isFalse
    }

    @Test
    fun `null과 Product를 비교하면 false를 반환한다`() {
        assertThat(product.equals(null)).isFalse
    }

    @Test
    fun `Product 와 Product가 아닌 타입을 비교하면 false를 반환한다`() {
        assertThat(product.equals("상품")).isFalse
        assertThat(product.equals(1234)).isFalse
    }
}
