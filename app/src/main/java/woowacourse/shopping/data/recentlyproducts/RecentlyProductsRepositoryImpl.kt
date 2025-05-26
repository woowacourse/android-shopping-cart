package woowacourse.shopping.data.recentlyproducts

import woowacourse.shopping.data.mapper.toRecentEntity
import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class RecentlyProductsRepositoryImpl(
    private val dao: RecentlyProductsDao,
) : RecentlyProductsRepository {
    override fun insert(product: Product) {
        var productId: RecentlyProductsEntity? = null
        productId = product.toRecentEntity()
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

    override fun getAll(): List<Long>? {
        var recentProducts: List<Long>? = null
        thread {
            recentProducts = dao.getAll()
        }.join()
        return recentProducts
    }
}
