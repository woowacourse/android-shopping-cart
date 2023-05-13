package woowacourse.shopping.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class RecentProductsTest {
    @Test
    fun 최근_본_상품_9개를_요청하면_상품_9개를_반환한다() {
        // given
        val recentProducts = RecentProducts(
            List(10) { createRecentProduct() }
        )

        // when
        val actualRecentProducts = recentProducts.getRecentProducts(9)
        val actual = actualRecentProducts.value.size

        // then
        val expected = 9
        assertEquals(expected, actual)
    }
}
