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
            catalogProductRepository.getCartProductsByUids(uids) { products ->
                val entities = products.map { it.toEntity() }
                callback(entities)
            }
        }
    }

    override fun getLatestViewedProduct(callback: (ProductUiModel) -> Unit) {
        thread {
            val uid = recentlyViewedProductDao.getLatestViewedProductUid()
            catalogProductRepository.getCartProductsByUids(listOf(uid)) { products ->
                val first = products.first()
                callback(first)
            }
        }
    }
}
