package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
            id = "1",
            name = "임시",
            price = Money(1000),
            imageUrl = "",
        )

        val other = Product(
            id = "1",
            name = "임시2",
            price = Money(2000),
            imageUrl = "",
        )

        // when : 동일한 id 상품을 입력받아 비교할 때
        val result = product.isSame(
            other,
        )

        // then : true를 반환한다
        assertEquals(true, result)
    }

    @Test
    fun `입력받은 상품의 id가 다르면 false를 반환한다`() {
        // given : 상품과 다른 상품이 주어진다
        val product = Product(
            id = "1",
            name = "임시",
            price = Money(1000),
            imageUrl = "",
        )

        val other = Product(
            id = "2",
            name = "임시",
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
