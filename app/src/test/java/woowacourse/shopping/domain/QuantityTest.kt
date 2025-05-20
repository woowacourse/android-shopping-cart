package woowacourse.shopping.domain

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class QuantityTest {
    @Test
    fun `수량은_0이하로 감소하지 않는다`() {
        // when
        val quantity = Quantity(0)

        // given
        val expected = quantity - 1

        // then
        assertEquals(expected.value, 0)
    }

    @Test
    fun `수량이 증가한다`() {
        // when
        val quantity = Quantity(1)

        // given
        val expected = quantity + 1

        // then
        assertEquals(expected.value, 2)
    }
}
