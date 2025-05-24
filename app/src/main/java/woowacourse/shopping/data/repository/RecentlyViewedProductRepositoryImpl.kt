package woowacourse.shopping.data.repository

import woowacourse.shopping.data.CatalogDataSource
import woowacourse.shopping.data.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.data.entity.RecentlyViewedProductEntity
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.product.catalog.ProductUiModel
import kotlin.concurrent.thread

class RecentlyViewedProductRepositoryImpl(
    val recentlyViewedProductDao: RecentlyViewedProductDao,
    val catalogDataSource: CatalogDataSource,
) : RecentlyViewedProductRepository {
    override fun insertRecentlyViewedProductUid(uid: Int) {
        thread {
            recentlyViewedProductDao.insertRecentlyViewedProductUid(RecentlyViewedProductEntity(uid))
        }
    }

    override fun getRecentlyViewedProducts(callback: (List<CartProductEntity>) -> Unit) {
        thread {
            val uids = recentlyViewedProductDao.getRecentlyViewedProductUids()
            val items = catalogDataSource.getCartProductsByUids(uids).map { it.toEntity() }
            callback(items)
        }
    }

    override fun getLatestViewedProduct(callback: (ProductUiModel) -> Unit) {
        thread {
            val uid = recentlyViewedProductDao.getLatestViewedProductUid()
            val item = catalogDataSource.getCartProductsByUids(listOf(uid)).firstOrNull()
            item?.let { callback(item) }
        }
    }
}
