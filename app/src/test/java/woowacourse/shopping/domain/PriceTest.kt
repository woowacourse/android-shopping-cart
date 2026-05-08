package woowacourse.shopping.domain

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PriceTest {
    @Test
    fun `가격이 0원 미만일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Price(-1)
        }
    }

    @Test
    fun `가격은 곱할 수 있다`() {
        val price = Price(10_000)
        val quantity = 3

        (price * quantity) shouldBe Price(30_000)
    }
}
