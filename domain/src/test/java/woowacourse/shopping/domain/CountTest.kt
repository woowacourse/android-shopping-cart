package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CountTest {
    @Test
    fun `상품 갯수는 음수일 수 없다`() {
        assertThrows<IllegalArgumentException> { Count(-3) }
    }

    @Test
    fun `0인지 판별한다`() {
        assertThat(Count(0).isZero()).isEqualTo(true)
    }
}
