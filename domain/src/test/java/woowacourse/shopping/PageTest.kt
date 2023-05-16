package woowacourse.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PageTest {
    @Test
    fun `Page 값은 1 이상이어야 한다`() {
        assertThrows<IllegalArgumentException> { Page(0) }
    }

    @Test
    fun `값이 1인 페이지에 2를 더하면 값이 3인 Page 를 반환한다 `() {
        // given
        val page = Page(1)
        // when
        val actual = (page + 2).value
        // then
        val expected = 3
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `값이 3인 페이지에 2를 빼면 값이 1인 Page 를 반환한다 `() {
        // given
        val page = Page(3)
        // when
        val actual = (page - 2).value
        // then
        val expected = 1
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `값이 3인 페이지의 limit 이 5라면, offset 은 10이다 `() {
        // given
        val page = Page(3)
        val limit = 5
        // when
        val actual = page.getOffset(limit)
        // then
        val expected = 10
        assertThat(actual).isEqualTo(expected)
    }
}
