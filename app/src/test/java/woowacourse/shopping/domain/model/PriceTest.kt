package woowacourse.shopping.domain.model

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class PriceTest {
    @Test
    fun `가격이 음수인 경우 생성이 불가능하다`() {
        assertThrows(IllegalArgumentException::class.java) { Price(-1) }
    }
}
