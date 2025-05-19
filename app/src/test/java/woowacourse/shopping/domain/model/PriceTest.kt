package woowacourse.shopping.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PriceTest {
    @Test
    fun `금액이 0보다 작으면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Price(-1)
        }
    }

    @Test
    fun `금액이 정상적으로 설정된다`() {
        val price = Price(1000)

        assertThat(price.value).isEqualTo(1000)
    }
}
