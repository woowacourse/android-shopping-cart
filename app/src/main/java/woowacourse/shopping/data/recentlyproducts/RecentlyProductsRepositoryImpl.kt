package woowacourse.shopping.data.recentlyproducts

import woowacourse.shopping.data.mapper.toRecentEntity
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class RecentlyProductsRepositoryImpl(
    private val dao: RecentlyProductsDao,
) : RecentlyProductsRepository {
    override fun insert(
        product: Product,
        callback: (() -> Unit)?,
    ) {
        val entity = product.toRecentEntity()
        thread {
            dao.insert(entity)
            callback?.invoke()
        }
    }

    override fun getFirst(callback: (Long?) -> Unit) {
        thread {
            val recentProduct = dao.getRecent()
            callback(recentProduct)
        }
    }

    override fun getAll(callback: (List<Long>?) -> Unit) {
        var recentProducts: List<Long>? = null
        thread {
            recentProducts = dao.getAll()
            callback(recentProducts)
        }
    }

    override fun deleteMostRecent() {
        thread {
            dao.deleteMostRecent()
        }
    }
}
