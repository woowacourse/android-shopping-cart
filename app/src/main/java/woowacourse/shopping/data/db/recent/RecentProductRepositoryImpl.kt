package woowacourse.shopping.data.db.recent

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.domain.model.toRecentProductEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread

class RecentProductRepositoryImpl(recentProductDatabase: RecentProductDatabase) :
    RecentProductRepository {
    private val dao = recentProductDatabase.recentProductDao()

    override fun save(product: Product) {
        if (findOrNullByProductId(product.id) != null) {
            update(product.id)
        } else {
            thread {
                dao.save(product.toRecentProductEntity())
            }.join()
        }
    }

    override fun update(productId: Long) {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        thread {
            dao.update(productId, now.format(formatter))
        }.join()
    }

    override fun findOrNullByProductId(productId: Long): RecentProduct? {
        var recentProductEntity: RecentProductEntity? = null
        thread {
            recentProductEntity = dao.findByProductId(productId)
        }.join()
        return recentProductEntity?.toRecentProduct()
    }

    override fun findMostRecentProduct(): RecentProduct? {
        var recentProduct: RecentProductEntity? = null
        thread {
            recentProduct = dao.findMostRecentProduct()
        }.join()
        return recentProduct?.toRecentProduct()
    }

    override fun findAll(limit: Int): List<RecentProduct> {
        var recentProducts: List<RecentProduct> = emptyList()
        thread {
            recentProducts = dao.findAll(limit).map { it.toRecentProduct() }
        }.join()
        return recentProducts
    }

    override fun deleteAll() {
        thread {
            dao.deleteAll()
        }.join()
    }
}
