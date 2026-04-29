package woowacourse.shopping.domain.model

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class QuantityTest {
    @Test
    fun `수량이 0인 경우 생성이 불가능하다`() {
        assertThrows(IllegalArgumentException::class.java) { Quantity(0) }
    }

    @Test
    fun `수량이 음수인 경우 생성이 불가능하다`() {
        assertThrows(IllegalArgumentException::class.java) { Quantity(-1) }
    }
}
