package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class PriceTest {
    @ParameterizedTest
    @ValueSource(ints = [-100, -2500, -10000])
    fun `0보다 작은 가격은 예외를 발생시킨다`(priceValue: Int) {
        assertThrows<IllegalArgumentException> { Price(priceValue) }
    }

    @ParameterizedTest
    @CsvSource("100,5,500", "1000,10,10000", "2400,3,7200")
    fun `상품 가격의 수량만큼 곱하여 총 가격을 알 수 있다`(
        priceValue: Int,
        quantity: Int,
        expectedPrice: Int,
    ) {
        val actualPrice = Price(priceValue).times(quantity)
        assertThat(actualPrice.value).isEqualTo(expectedPrice)
    }
}
