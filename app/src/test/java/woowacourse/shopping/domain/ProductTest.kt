package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductTest {
    @Test
    fun `상품은 id, 이름, 가격, 이미지 URL을 갖는다`() {
        assertDoesNotThrow {
            Product(
                id = "1",
                name = "product",
                price = 10_000,
                imageUrl = "",
            )
        }
    }

    @Test
    fun `상품의 이름이 공백일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Product(
                id = "1",
                name = "       ",
                price = 10_000,
                imageUrl = "",
            )
        }
    }

    @Test
    fun `상품의 가격이 0원 미만일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Product(
                id = "1",
                name = "product",
                price = -1,
                imageUrl = "",
            )
        }
    }
}
