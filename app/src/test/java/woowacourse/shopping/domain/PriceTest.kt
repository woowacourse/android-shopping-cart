package woowacourse.shopping.domain

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.shopping.domain.model.Price

class PriceTest {
    @ParameterizedTest
    @ValueSource(ints = [0, 1, 100, 9999])
    fun `가격이 0원 이상이면 예외를 던지지 않는다`(value: Int) {
        assertDoesNotThrow { Price(value) }
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, -100, -9999])
    fun `가격이 0원 미만이면 예외를 던진다`(value: Int) {
        assertThrows<IllegalArgumentException> { Price(value) }
    }
}
