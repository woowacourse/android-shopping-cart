package woowacourse.shopping.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class PageNumberTest {

    @ParameterizedTest
    @ValueSource(ints = [0, -1, -2, -3, -4, -5])
    internal fun `페이지 번호가 1보다 작으면 예외가 발생한다`(number: Int) {
        assertThrows<IllegalArgumentException> { PageNumber(number) }
    }

    @ParameterizedTest
    @ValueSource(ints = [2, 3, 4, 5, 6, 100])
    internal fun `페이지 번호가 1보다 크면 이전 페이지가 존재한다`(number: Int) {
        // given
        val page = PageNumber(number)

        // when
        val actual = page.hasPrevious()

        // then
        assertThat(actual).isTrue
    }

    @Test
    internal fun `페이지 번호가 1이면 이전 페이지가 존재하지 않는다`() {
        // given
        val page = PageNumber(1)

        // when
        val actual = page.hasPrevious()

        // then
        assertThat(actual).isFalse
    }

    @Test
    internal fun `페이지 번호가 1이면, 감소 시켰을 때 더 이상 감소하지 않는다`() {
        // given
        val expected = PageNumber(1)
        var page = PageNumber(1)

        // when
        val actual = --page

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(ints = [2, 3, 4, 5, 6, 100])
    internal fun `페이지 번호가 1보다 크면, 감소 시켰을 때 1만큼 감소한다`(currentNumber: Int) {
        // given
        var page = PageNumber(currentNumber)
        val expected = PageNumber(currentNumber - 1)

        // when
        val actual = --page

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(ints = [2, 3, 4, 5, 6, 100])
    internal fun `페이지 번호를 증가시켰을 때, 1만큼 증가한다`(currentNumber: Int) {
        // given
        var page = PageNumber(currentNumber)
        val expected = PageNumber(currentNumber + 1)

        // when
        val actual = ++page

        // then
        assertThat(actual).isEqualTo(expected)
    }
}
