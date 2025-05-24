package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.CartProductDao
import woowacourse.shopping.data.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.entity.CartProductEntity
import woowacourse.shopping.data.entity.RecentlyViewedProductEntity
import kotlin.concurrent.thread

class RecentlyViewedProductRepositoryImpl(
    val recentlyViewedProductDao: RecentlyViewedProductDao,
    val cartProductDao: CartProductDao,
) : RecentlyViewedProductRepository {
    override fun insertRecentlyViewedProductUid(uid: Int) {
        thread {
            recentlyViewedProductDao.insertRecentlyViewedProductUid(RecentlyViewedProductEntity(uid))
        }
    }

    override fun getRecentlyViewedProducts(callback: (List<CartProductEntity>) -> Unit) {
        thread {
            val uids = recentlyViewedProductDao.getRecentlyViewedProductUids()
            val items = cartProductDao.getCartProductsByUids(uids)
            callback(items)
        }
    }
}
