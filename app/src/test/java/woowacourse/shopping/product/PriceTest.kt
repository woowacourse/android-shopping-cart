package woowacourse.shopping.product

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.shopping.domain.product.model.Price

class PriceTest {
    @ParameterizedTest
    @ValueSource(ints = [-1, -2, -100, -1000, -2000, -3000])
    fun `가격의 값이 음수일 경우 예외가 발생한다`(value: Int) {
        assertThrows(IllegalArgumentException::class.java) {
            Price(value)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 2, 100, 1000, 2000, 3000])
    fun `가격이 0원이상일 경우 예외가 발생하지 않는다`(value: Int) {
        assertTrue(Price.isValueValid(value))
    }
}
