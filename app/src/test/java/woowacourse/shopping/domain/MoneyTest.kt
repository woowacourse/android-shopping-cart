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
    fun `두 Money를 더하면 금액의 합인 Money를 반환한다`() {
        // given : 1000원 Money와 2000원 Money가 주어진다
        val money = Money(1000)
        val otherMoney = Money(2000)

        // when : 두 Money를 더 했을 때
        val newMoney = money + otherMoney

        // then : 3000원 Money가 반환된다
        assertEquals(3000, newMoney.amount)
    }

    @Test
    fun `두 Money를 빼면 금액의 차인 Money를 반환한다`() {
        // given : 1000원 Money와 2000원 Money가 주어진다
        val money = Money(1000)
        val otherMoney = Money(2000)

        // when : 두 Money를 뺏을 때
        val newMoney = otherMoney - money

        // then : 1000원 Money가 반환된다
        assertEquals(1000, newMoney.amount)
    }

    @Test
    fun `Money에 수량(Int)을 곱하면 금액이 곱해진 Money를 반환한다`() {
        // given : 1000원 Money가 주어진다
        val money = Money(1000)

        // when : Money에 10을 곱했을 때
        val newMoney = money * 10

        // then : 10000원 Money가 반환된다
        assertEquals(10000, newMoney.amount)
    }
}
