package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MoneyTest {
    @Test
    fun `가격이 0원 이상이어야 한다`() {
        // given & when & then : 1000원짜리 상품을 만들면, 오류가 발생하지 않는다
        assertDoesNotThrow {
            Money(1000)
        }
    }

    @Test
    fun `가격이 0원 미만이면 오류가 발생한다`() {
        // given & when & then : -1원짜리 상품을 만들면, 오류가 발생한다
        assertThrows<IllegalArgumentException> {
            Money(-1)
        }
    }
}
