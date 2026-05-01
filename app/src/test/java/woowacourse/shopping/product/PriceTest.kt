package woowacourse.shopping.product

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.product.Price

class PriceTest {
    @Test
    fun `가격의 값이 음수일 경우 예외가 발생한다`() {
        val value = -10000

        assertThrows(IllegalArgumentException::class.java) {
            Price(value)
        }
    }

    @Test
    fun `가격이 0원일 경우 예외가 발생하지 않는다`() {
        val value = 0
        assertTrue(Price(value).value == value)
    }
}
