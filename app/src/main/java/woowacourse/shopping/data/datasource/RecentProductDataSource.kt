package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.entity.RecentProductEntity
import java.time.LocalDateTime

interface RecentProductDataSource {
    fun insert(recentProduct: RecentProductEntity)

    fun lastRecentProduct(): RecentProductEntity?

    fun recentByProductId(productId: Long): RecentProductEntity

    fun recentProducts(): List<RecentProductEntity>

    fun updateLocalDateTime(
        productId: Long,
        localDateTime: LocalDateTime,
    )
}
