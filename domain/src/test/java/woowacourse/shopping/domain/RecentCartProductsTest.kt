package woowacourse.shopping.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class RecentCartProductsTest {
    @Test
    fun 최근_본_상품_9개를_요청하면_상품_9개를_반환하다() {
        // given
        val recentProducts = RecentProducts(
            List(10) { makeRecentProductMock(ordinal = it) }
        )

        // when
        val actualRecentProducts = recentProducts.getRecentProducts(9)
        val actual = actualRecentProducts.value.size

        // then
        val expected = 9
        assertEquals(expected, actual)
    }
}
