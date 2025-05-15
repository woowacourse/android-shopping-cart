package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PriceTest {
    @ParameterizedTest
    @ValueSource(ints = [-1, -1000, -18312983])
    fun `가격은_0원_이상이다`(value: Int) {
        assertThatThrownBy { Price(value) }.isInstanceOf(IllegalArgumentException::class.java)
    }
}
