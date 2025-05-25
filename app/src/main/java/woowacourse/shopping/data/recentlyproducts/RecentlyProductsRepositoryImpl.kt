package woowacourse.shopping.data.recentlyproducts

import kotlin.concurrent.thread

class RecentlyProductsRepositoryImpl(
    private val dao: RecentlyProductsDao,
) : RecentlyProductsRepository {
    override fun insert(productId: Long) {
        thread {
            dao.insert(productId)
        }.join()
    }

    override fun getFirst(): Long {
        var recentProduct: Long? = null
        thread {
            recentProduct = dao.getRecent()
        }
        return recentProduct ?: throw IllegalArgumentException()
    }

    override fun getAll(): List<Long> {
        var recentProducts: List<Long>? = null
        thread {
            recentProducts = dao.getAll()
        }
        return recentProducts ?: throw IllegalArgumentException()
    }
}
