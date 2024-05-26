package woowacourse.shopping.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.lang.IllegalArgumentException

class QuantityTest {
    @Test
    fun `상품 개수의 초기값은 0이다`() {
        val quantity = Quantity()
        assertThat(quantity.count).isEqualTo(0)
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, -10, -1000])
    fun `초기값보다 작은 상품 개수는 예외가 발생한다`(count: Int) {
        assertThrows<IllegalArgumentException> { Quantity(count) }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 5, 10, 1000])
    fun `초기값보다 같거나 큰 상품 개수는 예외가 발생하지 않는다`(count: Int) {
        assertDoesNotThrow { Quantity(count) }
    }

    @Test
    fun `상품 개수가 3개일 때 증가시키면 4개가 된다`() {
        var quantity = Quantity(3)
        quantity++
        assertThat(quantity.count).isEqualTo(4)
    }

    @Test
    fun `상품 개수가 99개일 때 증가시켜도 변화가 없다`() {
        var quantity = Quantity(99)
        quantity++
        assertThat(quantity.count).isEqualTo(99)
    }

    @Test
    fun `상품 개수가 3개일 때 감소시키면 2개가 된다`() {
        var quantity = Quantity(3)
        quantity--
        assertThat(quantity.count).isEqualTo(2)
    }

    @Test
    fun `상품 개수가 0개일 때 감소시켜도 변화가 없다`() {
        var quantity = Quantity(0)
        quantity--
        assertThat(quantity.count).isEqualTo(0)
    }

    @Test
    fun `상품 개수가 0개면 상품 개수의 최소값이다`() {
        val quantity = Quantity(0)
        val actual = quantity.isMin()
        assertThat(actual).isTrue
    }

    @Test
    fun `상품 개수가 5개면 상품 개수의 최소값이 아니다`() {
        val quantity = Quantity(5)
        val actual = quantity.isMin()
        assertThat(actual).isFalse
    }

    @Test
    fun `상품 개수가 5개면 상품 개수의 최소값보다 크다`() {
        val quantity = Quantity(5)
        val actual = quantity.isGreaterThanMin()
        assertThat(actual).isTrue
    }

    @Test
    fun `상품 개수가 0개면 상품 개수의 최소값보다 크지 않다`() {
        val quantity = Quantity(0)
        val actual = quantity.isGreaterThanMin()
        assertThat(actual).isFalse
    }
}
