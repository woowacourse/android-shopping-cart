package woowacourse.shopping.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CounterTest {

    @Test
    fun `카운터가 1일 때 1 증가하면 2가 된다`() {
        // given
        val counter = Counter(1)

        // when
        val actual = counter + 1

        // then
        val expected = Counter(2)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `카운터가 2일 때 1 감소하면 1이 된다`() {
        // given
        val counter = Counter(2)

        // when
        val actual = counter - 1

        // then
        val expected = Counter(1)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `카운터가 1일 때 더 이상 감소할 수 없다`() {
        // given
        val counter = Counter(1)

        // when
        val actual = counter - 1

        // then
        val expected = Counter(1)
        assertThat(actual).isEqualTo(expected)
    }
}
