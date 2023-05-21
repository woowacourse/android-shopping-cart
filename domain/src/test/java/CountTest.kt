import model.Count
import org.junit.Assert.assertEquals
import org.junit.Test

class CountTest {

    @Test(expected = IllegalArgumentException::class)
    fun `개수가 0이하가 되면 예외가 발생한다`() {
        Count(0)
    }

    @Test
    fun `개수를 증가시키면 현재 가지고 있는 수보다 1이 더 큰 수를 가진 인스턴스를 반환한다 `() {
        // given
        val count = Count(1)

        // when
        val actual = count.plus()

        // then
        val expected = Count(2)
        assertEquals(expected, actual)
    }

    @Test
    fun `개수를 감소시키면 현재 가지고 있는 수보다 1이 더 작은 수를 가진 인스턴스를 반환한다 `() {
        // given
        val count = Count(2)

        // when
        val actual = count.minus()

        // then
        val expected = Count(1)
        assertEquals(expected, actual)
    }

    fun `개수가 1일때 개수를 감소시킨 경우 개수가 1인 인스턴스를 반환한다`() {
        // given
        val count = Count(1)

        // when
        val actual = count.minus()

        // then
        val expected = Count(1)
        assertEquals(expected, actual)
    }
}
