package woowacourse.shopping.domain.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class PriceTest {
    @Test
    fun `가격을 가진다`() {
        // given
        val expected = 12_000

        // when
        val actual = Price(expected).value

        // then
        actual shouldBe expected
    }

    @Test
    fun `가격은 0원 이상이어야 한다`() {
        shouldThrow<IllegalArgumentException> {
            Price(-1)
        }
    }
}
