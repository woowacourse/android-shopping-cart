package woowacourse.shopping.data.recentlyproducts

import woowacourse.shopping.data.mapper.toRecentEntity
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class RecentlyProductsRepositoryImpl(
    private val dao: RecentlyProductsDao,
) : RecentlyProductsRepository {
    override fun insert(
        product: Product,
        onLoad: (Result<Unit>) -> Unit,
    ) {
        {
            val entity = product.toRecentEntity()
            dao.insert(entity)
        }.runAsync(onLoad)
    }

    override fun getFirst(onLoad: (Result<Long?>) -> Unit) {
        {
            dao.getRecent()
        }.runAsync(onLoad)
    }

    override fun getAll(onLoad: (Result<List<Long>?>) -> Unit) {
        {
            dao.getAll()
        }.runAsync(onLoad)
    }

    override fun deleteMostRecent(onLoad: (Result<Unit>) -> Unit) {
        {
            dao.deleteMostRecent()
        }.runAsync(onLoad)
    }

    private inline fun <T> (() -> T).runAsync(crossinline onResult: (Result<T>) -> Unit) {
        thread {
            val result = runCatching(this)
            onResult(result)
        }
    }
}
