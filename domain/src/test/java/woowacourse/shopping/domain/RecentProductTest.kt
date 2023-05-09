package woowacourse.shopping.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class RecentProductTest {

    @Test
    fun 최근_본_상품이_9개일_때_상품을_넣으면_10개가_된다() {
        // given
        val recentProduct = RecentProduct(
            10,
            List(9) { createProduct() }
        )

        // when
        val actualRecentProduct = recentProduct.add(createProduct())
        val actual = actualRecentProduct.products.size

        // then
        val expected = 10
        assertEquals(expected, actual)
    }

    @Test
    fun 최근_본_상품이_10개일_때_상품을_넣으면_그대로_10개이다() {
        // given
        val recentProduct = RecentProduct(
            10,
            List(10) { createProduct() }
        )

        // when
        val actualRecentProduct = recentProduct.add(createProduct())
        val actual = actualRecentProduct.products.size

        // then
        val expected = 10
        assertEquals(expected, actual)
    }
}
