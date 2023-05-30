package woowacourse.shopping

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CounterTest {
    @Test
    fun `2를 더하면 2증가된 카운터를 반환한다`() {
        // given
        val counter = Counter(1)
        // when
        val actual = counter + 2
        // given
        val expected = 3
        assertThat(actual.value).isEqualTo(expected)
    }

    @Test
    fun `2를 빼면 2감소된 카운터를 반환한다`() {
        // given
        val counter = Counter(3)
        // when
        val actual = counter - 2
        // given
        val expected = 1
        assertThat(actual.value).isEqualTo(expected)
    }

    @Test
    fun `뺏을 때, 지정한 최소 숫자보다 작으면 최소 숫자 카운터를 반환한다`() {
        // given
        val counter = Counter(value = 1, minimumCount = 1)
        // when
        val actual = counter - 2
        // given
        val expected = 1
        assertThat(actual.value).isEqualTo(expected)
    }
}
