package woowacourse.shopping.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class PriceTest {
    @Test
    fun `음수인 가격은 예외가 발생한다`() {
        shouldThrow<IllegalArgumentException> {
            Price(-1000)
        }
    }

    @Test
    fun `가격의 Int값을 가져올 수 있다`() {
        Price(1000).toInt() shouldBe 1000
    }
}
