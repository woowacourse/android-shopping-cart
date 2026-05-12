package woowacourse.shopping.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class QuantityTest {
    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun `수량이 0이상의 양수라면 생성된다`(value: Int) {
        Quantity(value).value shouldBe value
    }

    @Test
    fun `수량이 음수라면 예외가 발생한다`() {
        shouldThrow<IllegalArgumentException> { Quantity(-1) }
    }
}
