package woowacourse.shopping.data.db.recent

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.model.toRecentProductEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecentProductRepositoryImpl(recentProductDatabase: RecentProductDatabase) :
    RecentProductRepository {
    private val dao = recentProductDatabase.recentProductDao()

    override fun save(product: Product) {
        if (findOrNullByProductId(product.id) != null) {
            update(product.id)
        } else {
            threadAction {
                dao.save(product.toRecentProductEntity())
            }
        }
    }

    override fun update(productId: Long) {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        threadAction {
            dao.update(productId, now.format(formatter))
        }
    }

    override fun findOrNullByProductId(productId: Long): RecentProduct? {
        var recentProductEntity: RecentProductEntity? = null
        threadAction {
            recentProductEntity = dao.findByProductId(productId)
        }
        return recentProductEntity?.toRecentProduct()
    }

    override fun findMostRecentProduct(): RecentProduct? {
        var recentProduct: RecentProductEntity? = null
        threadAction {
            recentProduct = dao.findMostRecentProduct()
        }
        return recentProduct?.toRecentProduct()
    }

    override fun findAll(limit: Int): List<RecentProduct> {
        var recentProducts: List<RecentProduct> = emptyList()
        threadAction {
            recentProducts = dao.findAll(limit).map { it.toRecentProduct() }
        }
        return recentProducts
    }

    override fun deleteAll() {
        threadAction {
            dao.deleteAll()
        }
    }

    private fun threadAction(action: () -> Unit) {
        val thread = Thread(action)
        thread.start()
        thread.join()
    }
}
