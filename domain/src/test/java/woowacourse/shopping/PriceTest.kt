package woowacourse.shopping

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PriceTest {

    @Test
    fun `가격이 0원 이상이어야 한다`() {
        assertThrows<IllegalArgumentException> { Price(-100) }
    }
}
