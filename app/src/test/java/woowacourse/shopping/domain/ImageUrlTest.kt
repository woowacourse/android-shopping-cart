package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ImageUrlTest {

    @Test
    fun `ImageUrl은 url의 문자열 값을 갖는다`() {
        val imageUrl = ImageUrl(TEST_URL_STRING)
        assertThat(imageUrl.url).isEqualTo(TEST_URL_STRING)
    }

    @Test
    fun `isNone 메서드로 url이 비어있는지를 알 수 있다`() {
        val imageUrl = ImageUrl("dfd")
        assertThat(imageUrl.isNone()).isFalse()
    }

    companion object {
        private const val TEST_URL_STRING = "http://test.url"
    }
}
