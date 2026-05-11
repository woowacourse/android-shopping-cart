package woowacourse.shopping.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ProductTitleTest {
    @ParameterizedTest
    @ValueSource(strings = ["", "  ", "\n", "\t", "  \n "])
    fun `제목으로 빈 문자열이 들어오면 예외를 반환한다`(blankString: String) {
        shouldThrow<IllegalArgumentException> {
            ProductTitle(blankString)
        }
    }

    @Test
    fun `제목 값을 문자열로 가져올 수 있다`() {
        val title = "콩콩"
        ProductTitle(title).value shouldBe title
    }
}
