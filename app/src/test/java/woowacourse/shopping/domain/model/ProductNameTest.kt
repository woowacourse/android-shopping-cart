package woowacourse.shopping.domain.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductNameTest {
    @Test
    fun `이름이 빈 값일 경우 예외를 반환한다`() {
        assertThrows<IllegalArgumentException> {
            ProductName(name = "")
        }
    }

    @Test
    fun `이름이 공백일 경우 예외를 반환한다`() {
        assertThrows<IllegalArgumentException> {
            ProductName(name = "    ")
        }
    }
}
