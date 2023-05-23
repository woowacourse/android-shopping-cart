package woowacourse.shopping.domain.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProductCountTest {
    @Test
    internal fun `제품 개수를 증가시킨다`() {
        // given
        val productCount = ProductCount(1, 1)

        // when
        val actual = productCount.plus(2)

        // then
        assertEquals(actual, ProductCount(3, 1))
    }

    @Test
    internal fun `제품 개수를 감소시킬 때 최소 개수보다 줄어들 수 없다`() {
        // given
        val productCount = ProductCount(5, 1)

        // when
        val actual = productCount.minus(10)

        // then
        assertEquals(actual, ProductCount(1, 1))
    }
}
