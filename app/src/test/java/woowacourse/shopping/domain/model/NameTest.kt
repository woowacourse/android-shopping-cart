package woowacourse.shopping.domain.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class NameTest {
    @Test
    fun `이름을 가진다`() {
        // given
        val expected = "Test"

        // when
        val actual = Name(expected).value

        // then
        actual shouldBe expected
    }

    @Test
    fun `이름은 존재해야 한다`() {
        shouldThrow<IllegalArgumentException> {
            Name("")
        }
    }
}
