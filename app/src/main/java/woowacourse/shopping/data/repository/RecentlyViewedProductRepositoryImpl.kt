package woowacourse.shopping.data.repository

import android.util.Log
import woowacourse.shopping.data.CatalogDataSource
import woowacourse.shopping.data.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.data.entity.RecentlyViewedProductEntity
import woowacourse.shopping.data.mapper.toEntity
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
            Log.d("REPOSITORY", "$uids")
            val items = catalogDataSource.getCartProductsByUids(uids).map { it.toEntity() }
            Log.d("REPOSITORY", "$items")
            callback(items)
        }
    }
}
