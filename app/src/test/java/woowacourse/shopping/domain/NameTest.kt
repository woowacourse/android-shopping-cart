package woowacourse.shopping.domain

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NameTest {
    @ParameterizedTest
    @ValueSource(strings = [""])
    fun `이름에 빈 값이 들어오면 예외를 반환한다`(value: String) {
        assertThrows<IllegalArgumentException> { Name(value) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["     ", "  "])
    fun `이름에 공백이 들어오면 예외를 반환한다`(value: String) {
        assertThrows<IllegalArgumentException> { Name(value) }
    }
}
