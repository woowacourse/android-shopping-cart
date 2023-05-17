package woowacourse.shopping.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PriceTest {
    @Test
    fun `상품 가격은 음수일 수 없다`() {
        assertThrows<IllegalArgumentException> { Price(-3) }
    }
}
