package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@JvmInline
value class Quantity(private val quantity: Int) {
    init {
        require(quantity > 0) { "수량은 1보다 작을 수 없습니다. 수량 : $quantity" }
    }

    operator fun plus(other: Quantity): Quantity = Quantity(this.quantity + other.quantity)
}

class QuantityTest {
    @Test
    fun `수량이 1개 미만이면 오류가 발생한다`() {
        // given & when & then : 0개인 수량이 주어지면 오류가 발생한다
        assertThrows<IllegalArgumentException> {
            Quantity(0)
        }
    }

    @Test
    fun `수량을 더하면 더해진 새 Quantity를 반환한다`() {
        // given : 1개인 수량이 주어진다
        val quantity = Quantity(1)

        // when : Quantity에 1을 더할 때
        val newQuantity = quantity + Quantity(1)

        // then : Quantity의 수량이 2가 된다
        assertEquals(Quantity(2), newQuantity)
    }
}
