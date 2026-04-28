package woowacourse.shopping.domain

import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class Product(
    private val name: String,
    private val price: Money,
    private val imageUrl: String,
    private val id: String = UUID.randomUUID().toString(),
) {
    init {
        require(name.isNotBlank()) { "상품 제목은 공백일 수 없습니다." }
    }
    fun isSame(other: Product): Boolean = this.id == other.id
}

class ProductTest {

    @Test
    fun `상품의 이름이 공백이면 오류가 발생한다`() {
        // given & when & then : 상품 이름이 공백이면 오류가 발생한다
        assertThrows<IllegalArgumentException> {
            Product(
                name = "  ",
                price = Money(1000),
                imageUrl = "",
            )
        }
    }

    @Test
    fun `입력받은 상품의 id가 같으면 true를 반환한다`() {
        // given : 상품이 주어진다
        val product = Product(
            name = "임시",
            price = Money(1000),
            imageUrl = "",
        )

        // when : 동일한 상품을 입력받아 비교할 때
        val result = product.isSame(
            product,
        )

        // then : true를 반환한다
        assertEquals(true, result)
    }

    @Test
    fun `입력받은 상품의 id가 다르면 false를 반환한다`() {
        // given : 상품과 다른 상품이 주어진다
        val product = Product(
            name = "임시",
            price = Money(1000),
            imageUrl = "",
        )

        val other = Product(
            name = "임시2",
            price = Money(1000),
            imageUrl = "",
        )

        // when : 다른 상품을 입력받아 비교할 때
        val result = product.isSame(
            other,
        )

        // then : false를 반환한다
        assertEquals(false, result)
    }
}
