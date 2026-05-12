package woowacourse.shopping.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductTest {

    @Test
    fun `상품의 이름이 공백이면 오류가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Product(
                name = "  ",
                price = Money(1000),
                imageUrl = "",
            )
        }
    }
}
