package woowacourse.shopping.core.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class MoneyTest {
    @Test
    fun `음수일 경우 예외를 반환한다`() {
        assertThrows<IllegalArgumentException> {
            Money(amount = -1)
        }
    }

    @Test
    fun `금액이 0일 경우 예외를 반환하지 않는다`() {
        assertDoesNotThrow {
            Money(amount = 0)
        }
    }

    @Test
    fun `Money에 정수를 곱하면 곱셈값을 금액으로 가진 Money를 반환한다`() {
        assertThat(
            Money(amount = 1000) * 3,
        ).isEqualTo(Money(amount = 3000))
    }
}
