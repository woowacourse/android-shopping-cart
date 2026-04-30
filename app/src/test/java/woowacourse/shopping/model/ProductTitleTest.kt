package woowacourse.shopping.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ProductTitleTest {
    @Test
    fun `제목으로 빈 문자열이 들어오면 예외를 반환한다`() {
        shouldThrow<IllegalArgumentException> {
            ProductTitle("")
        }
    }

    @Test
    fun `제목 값을 문자열로 가져올 수 있다`() {
        val title = "콩콩"
        ProductTitle(title).toString() shouldBe title
    }
}
