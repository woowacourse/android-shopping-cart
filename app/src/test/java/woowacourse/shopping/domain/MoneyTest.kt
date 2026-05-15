package woowacourse.shopping.domain

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MoneyTest {
    @Test
    fun `가격이 0원 이상이어야 한다`() {
        // given & when & then : 1000원짜리 상품을 만들면, 오류가 발생하지 않는다
        assertDoesNotThrow {
            Money(1000)
        }
    }

    @Test
    fun `가격이 0원 미만이면 오류가 발생한다`() {
        // given & when & then : -1원짜리 상품을 만들면, 오류가 발생한다
        assertThrows<IllegalArgumentException> {
            Money(-1)
        }
    }

    @Test
    fun `가격이 0원이면 오류가 발생하지 않는다`() {
        assertDoesNotThrow {
            Money(0)
        }
    }

    @Test
    fun `금액이 같은 두 Money는 동등하다`() {
        val money = Money(1000)
        val otherMoney = Money(1000)

        val result = money == otherMoney

        assertEquals(true, result)
    }

    @Test
    fun `두 Money를 더하면 금액의 합인 Money를 반환한다`() {
        val money = Money(1000)
        val otherMoney = Money(2000)

        val result = money + otherMoney

        assertEquals(Money(3000), result)
    }

    @Test
    fun `두 Money를 빼면 금액의 차가 0보다 작으면 에러가 발생한다`() {
        val money = Money(1000)
        val otherMoney = Money(2000)

        assertThrows<IllegalArgumentException> { money - otherMoney }
    }

    @Test
    fun `두 Money를 빼면 금액의 차인 Money를 반환한다`() {
        val money = Money(2000)
        val otherMoney = Money(1000)

        val result = money - otherMoney

        assertEquals(Money(1000), result)
    }

    @Test
    fun `Money에 수량(Quantity)을 곱하면 금액이 곱해진 Money를 반환한다`() {
        val money = Money(1000)
        val quantity = Quantity(3)

        val result = money * quantity

        assertEquals(Money(3000), result)
    }
}
