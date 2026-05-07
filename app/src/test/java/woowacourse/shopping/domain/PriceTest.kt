package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class PriceTest {
    @Test
    fun `가격이 0보다 작으면 예외를 던진다`() {
        assertThatThrownBy {
            Price(-100)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("가격은 0보다 크거나 같아야 합니다.")
    }
}
