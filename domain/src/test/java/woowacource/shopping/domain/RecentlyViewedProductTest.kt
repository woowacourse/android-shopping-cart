package woowacource.shopping.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentlyViewedProduct
import java.time.LocalDateTime

internal class RecentlyViewedProductTest {

    @Test
    fun `최근 본 상품의 아이디가 같으면 같다고 판단한다`() {
        val product = Product(1L, "imageUrl1", "name1", 10000)
        val oneRecentlyViewedProduct = RecentlyViewedProduct(product, LocalDateTime.MAX).apply { id = 1L }
        val otherRecentlyViewedProduct = RecentlyViewedProduct(product, LocalDateTime.MIN).apply { id = 1L }

        assertThat(oneRecentlyViewedProduct).isEqualTo(otherRecentlyViewedProduct)
    }

    @Test
    fun `최근 본 상품의 아이디가 다르면 다르다고 판단한다`() {
        val product = Product(1L, "imageUrl1", "name1", 10000)
        val oneRecentlyViewedProduct = RecentlyViewedProduct(product, LocalDateTime.MAX).apply { id = 1L }
        val otherRecentlyViewedProduct = RecentlyViewedProduct(product, LocalDateTime.MAX).apply { id = 2L }

        assertThat(oneRecentlyViewedProduct).isNotEqualTo(otherRecentlyViewedProduct)
    }

    @Test
    fun `최근 본 상품의 아이디가 널일 때 아이디를 널이 아닌 값으로 설정하면 아이디가 설정된다`() {
        val product = Product(1L, "imageUrl1", "name1", 10000)
        val recentlyViewedProduct = RecentlyViewedProduct(product, LocalDateTime.MAX)

        recentlyViewedProduct.id = 1L

        assertThat(recentlyViewedProduct.id).isEqualTo(1L)
    }

    @Test
    fun `최근 본 상품의 아이디가 널이 아닐 때 아이디를 널이 아닌 값으로 설정하면 아이디가 설정되지 않는다`() {
        val product = Product(1L, "imageUrl1", "name1", 10000)
        val recentlyViewedProduct = RecentlyViewedProduct(product, LocalDateTime.MAX).apply { id = 1L }

        recentlyViewedProduct.id = 2L

        assertThat(recentlyViewedProduct.id).isEqualTo(1L)
    }
}