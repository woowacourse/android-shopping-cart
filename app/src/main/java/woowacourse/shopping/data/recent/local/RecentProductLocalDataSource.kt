package woowacourse.shopping.data.recent.local

import woowacourse.shopping.data.recent.RecentProductEntity

class RecentProductLocalDataSource(
    private val dao: RecentProductDao,
) {
    fun insert(product: RecentProductEntity) = dao.insert(product)

    fun insertByProductId(productId: Long) = dao.insertByProductId(productId)

    fun getLastProduct(): RecentProductEntity? = dao.getLastProduct()

    fun getPaged(
        limit: Int,
        offset: Int,
    ): List<RecentProductEntity> = dao.getPaged(limit, offset)

    fun deleteByProductId(productId: Long) = dao.deleteByProductId(productId)

    fun replaceRecentProduct(recentProduct: RecentProductEntity) = dao.replaceRecentProduct(recentProduct)
}
