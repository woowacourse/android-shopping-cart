package woowacourse.shopping.product

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.product.model.ImageUrl

class ImageUrlTest {
    @Test
    fun `이미지 링크가 비어있으면 예외가 발생한다`() {
        val emptyImageUrl = ""
        assertThrows(IllegalArgumentException::class.java) {
            ImageUrl(emptyImageUrl)
        }
    }

    @Test
    fun `이미지 링크가 공백이면 예외가 발생한다`() {
        val emptyImageUrl = " "
        assertThrows(IllegalArgumentException::class.java) {
            ImageUrl(emptyImageUrl)
        }
    }
}
