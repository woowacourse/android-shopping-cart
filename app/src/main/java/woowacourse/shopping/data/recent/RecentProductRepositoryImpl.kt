package woowacourse.shopping.data.recent

import woowacourse.shopping.data.toEntity
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct
import woowacourse.shopping.view.inventory.item.RecentProduct
import kotlin.concurrent.thread

class RecentProductRepositoryImpl(private val recentProductDao: RecentProductDao) :
    RecentProductRepository {
    override fun getMostRecent(
        count: Int,
        onSuccess: (List<RecentProduct>) -> Unit,
    ) {
        thread {
            val recentProducts = recentProductDao.getMostRecent(count)
            onSuccess(recentProducts)
        }
    }

    override fun getLastProductBefore(
        product: InventoryProduct,
        onResult: (RecentProduct?) -> Unit,
    ) {
        thread {
            val lastProduct = recentProductDao.getLastProductBefore(product.id)
            onResult(lastProduct)
        }
    }

    override fun insert(recentProduct: RecentProduct) {
        thread {
            recentProductDao.insert(recentProduct.toEntity())
        }
    }
}
