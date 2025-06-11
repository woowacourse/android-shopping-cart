package woowacourse.shopping.data.product.dataSource

import woowacourse.shopping.data.product.dao.RecentViewedProductDao
import woowacourse.shopping.data.product.entity.RecentViewedProductEntity

object LocalRecentViewedProductsDataSource : RecentViewedProductsDataSource {
    private lateinit var dao: RecentViewedProductDao

    fun init(recentViewedProductDao: RecentViewedProductDao) {
        dao = recentViewedProductDao
    }

    override fun load(): List<RecentViewedProductEntity> = dao.load()

    override fun upsert(product: RecentViewedProductEntity) {
        val count = dao.count()
        if (count < MAX_ENTITY_COUNT) {
            dao.upsert(product)
            return
        }

        dao.deleteOldest(count - MAX_ENTITY_COUNT)
        dao.upsert(product)
    }

    const val MAX_ENTITY_COUNT = 30
}
