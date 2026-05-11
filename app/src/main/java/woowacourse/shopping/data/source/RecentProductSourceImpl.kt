package woowacourse.shopping.data.source

import woowacourse.shopping.data.source.local.recent.RecentProductDao
import woowacourse.shopping.data.source.local.recent.RecentProductEntity

class RecentProductSourceImpl(
    private val dao: RecentProductDao,
) : RecentProductSource {
    override suspend fun getRecentProductIds(): List<RecentProductEntity> = dao.getRecentProduct()

    override suspend fun addRecentProductId(productId: String) {
        val viewTime = System.currentTimeMillis()

        if (getRecentProductIds().count { it.productId == productId } > 0) {
            dao.update(
                recentProduct = RecentProductEntity(
                    productId = productId,
                    viewTime = viewTime,
                ),
            )

            return
        }

        dao.insert(
            recentProduct = RecentProductEntity(
                productId = productId,
                viewTime = viewTime,
            ),
        )
    }

    override suspend fun getLastViewProductId(): RecentProductEntity = getRecentProductIds().first()
}
