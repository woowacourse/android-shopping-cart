package woowacourse.shopping.data.recent

import android.content.Context
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct

class DummyRecentProductDataSource(context: Context) : RecentProductDataSource {
    private val recentProductDao = RecentProductDatabase.getInstance(context).dao()
    private val PRODUCT_AMOUNT = 10
    private lateinit var recent: List<RecentProductEntity>
    private val products: List<RecentProduct> get() = recent.map { it.toDomainModel() }

    override fun recentProducts(): List<RecentProduct> {
        val thread = Thread {
            recent = recentProductDao.getRecentProductsByPaging(PRODUCT_AMOUNT)
        }
        thread.start()
        thread.join()
        return products
    }

    override fun addRecentProduct(product: Product, viewTime: Long) {
        val thread = Thread {
            val existingProduct = productById(product.id)
            if (existingProduct != null) {
                recentProductDao.updateViewTime(product.id, viewTime)
            } else {
                val entity = RecentProductEntity.makeEntity(product, viewTime)
                recentProductDao.saveRecentProduct(entity)
            }
        }

        thread.start()
        thread.join()
    }

    override fun lastViewedProduct(): RecentProduct? {
        var result: RecentProductEntity? = null
        val thread = Thread {
            result = recentProductDao.getRecentProduct()
        }
        thread.start()
        thread.join()
        return result?.toDomainModel()
    }

    override fun productById(id: Long): Product? {
        return products.find { it.product.id == id }?.product
    }
}
