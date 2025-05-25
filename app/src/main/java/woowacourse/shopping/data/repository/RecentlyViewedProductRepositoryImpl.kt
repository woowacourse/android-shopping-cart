package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.data.entity.RecentlyViewedProductEntity
import woowacourse.shopping.data.mapper.toEntity
import woowacourse.shopping.product.catalog.ProductUiModel
import kotlin.concurrent.thread

class RecentlyViewedProductRepositoryImpl(
    val recentlyViewedProductDao: RecentlyViewedProductDao,
    val catalogProductRepository: CatalogProductRepository,
) : RecentlyViewedProductRepository {
    override fun insertRecentlyViewedProductUid(uid: Int) {
        thread {
            recentlyViewedProductDao.insertRecentlyViewedProductUid(RecentlyViewedProductEntity(uid))
        }
    }

    override fun getRecentlyViewedProducts(callback: (List<CartProductEntity>) -> Unit) {
        thread {
            val uids = recentlyViewedProductDao.getRecentlyViewedProductUids()
            val items = catalogProductRepository.getCartProductsByUids(uids).map { it.toEntity() }
            callback(items)
        }
    }

    override fun getLatestViewedProduct(callback: (ProductUiModel) -> Unit) {
        thread {
            val uid = recentlyViewedProductDao.getLatestViewedProductUid()
            val item = catalogProductRepository.getCartProductsByUids(listOf(uid)).firstOrNull()
            item?.let { callback(item) }
        }
    }
}
