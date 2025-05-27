package woowacourse.shopping.data.datasource.local

import woowacourse.shopping.data.dao.RecentProductDao
import woowacourse.shopping.data.entity.RecentProductEntity

class RecentProductLocalDataSource(
    private val dao: RecentProductDao,
) {
    fun getLastViewedProduct(): RecentProductEntity? = dao.getLastViewedProduct()

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): List<RecentProductEntity> = dao.getPagedProducts(limit, offset)

    fun deleteByProductId(productId: Long) = dao.deleteByProductId(productId)

    fun replaceRecentProduct(recentProductEntity: RecentProductEntity) = dao.replaceRecentProduct(recentProductEntity)
}
