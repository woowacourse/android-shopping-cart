package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CartContentQuantityTest {
    @Test
    fun `수량이 1개 미만이면 오류가 발생한다`() {
        // given & when & then : 0개인 수량이 주어지면 오류가 발생한다
        assertThrows<IllegalArgumentException> {
            CartContentQuantity(0)
        }
    }

    @Test
    fun `수량을 더하면 더해진 새 Quantity를 반환한다`() {
        // given : 1개인 수량이 주어진다
        val cartContentQuantity = CartContentQuantity(1)

        // when : Quantity에 1을 더할 때
        val newCartContentQuantity = cartContentQuantity + CartContentQuantity(1)

        // then : Quantity의 수량이 2가 된다
        assertEquals(CartContentQuantity(2), newCartContentQuantity)
    }
}
