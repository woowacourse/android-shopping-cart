package woowacourse.shopping.cart

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.cart.Quantity

class QuantityTest {
    @Test
    fun `수량은 0 이상이어야 한다`() {
        assertThrows(IllegalArgumentException::class.java) {
            Quantity(-1)
        }
    }

    @Test
    fun `increase 시 수량이 1 증가한다`() {
        val quantity = Quantity(3)

        val result = quantity.increase()

        assertEquals(Quantity(4), result)
    }

    @Test
    fun `decrease 시 수량이 1 감소한다`() {
        val quantity = Quantity(3)

        val result = quantity.decrease()

        assertEquals(Quantity(2), result)
    }

    @Test
    fun `decrease는 0 미만으로 내려가지 않는다`() {
        val quantity = Quantity(0)

        val result = quantity.decrease()

        assertEquals(Quantity(0), result)
    }

    @Test
    fun `isZero는 수량이 0일 때 true를 반환한다`() {
        assertTrue(Quantity(0).isZero)
        assertFalse(Quantity(1).isZero)
    }
}
