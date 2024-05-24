package woowacourse.shopping

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import java.time.LocalDateTime

interface ProductRepository {
    fun products(): List<Product>

    fun products(
        startPosition: Int,
        offset: Int,
    ): List<Product>

    fun productById(id: Long): Product

    fun productsTotalSize(): Int

    fun sortedRecentProduct(): List<RecentProduct>

    fun addRecentProduct(
        productId: Long,
        localDateTime: LocalDateTime,
    )
}
