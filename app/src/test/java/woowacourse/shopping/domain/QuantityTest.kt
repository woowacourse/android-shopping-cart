package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class QuantityTest {
    @Test
    fun `수량이 0개 이상이면 오류가 발생하지 않는다`() {
        // given & when & then : 0개인 수량이 주어지면 오류가 발생하지 않는다
        assertDoesNotThrow { Quantity(0) }
    }

    @Test
    fun `수량이 0보다 작으면 오류가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Quantity(-1)
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

    @Test
    fun `수량을 뺀 결과가 음수이면 에러가 발생한다`() {
        val quantity = Quantity(1)

        val newQuantity = Quantity(2) // 1 - 2 = -1

        assertThrows<IllegalArgumentException> { quantity - newQuantity }
    }

    @Test
    fun `수량을 빼면 빠진 새 Quantity를 반환한다`() {
        val quantity = Quantity(5)

        val newQuantity = Quantity(2)

        val result = quantity - newQuantity // 5 - 2 = 3

        assertEquals(Quantity(3), result)
    }

    @Test
    fun `수량이 같으면 두 Quantity는 동등하다`() {
        val quantity = Quantity(1)

        val newQuantity = Quantity(1)

        val result = quantity == newQuantity

        assertEquals(true, result)
    }
}
