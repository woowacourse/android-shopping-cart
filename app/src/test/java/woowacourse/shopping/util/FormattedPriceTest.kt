package woowacourse.shopping.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.util.formattedPrice

class FormattedPriceTest {
    @Test
    fun `1000 미만 숫자의 경우 구분점이 붙지 않는다`() {
        assertThat(
            formattedPrice(100),
        ).isEqualTo("100원")
    }

    @Test
    fun `숫자 포맷이 적용된다`() {
        assertThat(
            formattedPrice(10000),
        ).isEqualTo("10,000원")
    }
}
