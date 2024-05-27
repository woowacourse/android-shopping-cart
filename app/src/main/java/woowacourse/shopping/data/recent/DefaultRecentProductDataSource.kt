package woowacourse.shopping.data.recent

import android.content.Context
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.local.RecentProductDatabase
import woowacourse.shopping.local.entity.RecentProductEntity

class DefaultRecentProductDataSource(context: Context) : RecentProductDataSource {
    private val recentProductDao = RecentProductDatabase.getInstance(context).dao()
    private lateinit var recent: List<RecentProductEntity>
    private val products: List<RecentProduct> get() = recent.map { it.toDomainModel() }

    override fun recentProducts(size: Int): List<RecentProduct> {
        val thread =
            Thread {
                recent = recentProductDao.getRecentProductsByPaging(size)
            }
        thread.start()
        thread.join()
        return products
    }

    override fun addRecentProduct(
        product: Product,
        viewTime: Long,
    ) {
        val thread =
            Thread {
                val existingProduct = recentProductDao.getRecentProductById(product.id)
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
        val thread =
            Thread {
                result = recentProductDao.getRecentProduct()
            }
        thread.start()
        thread.join()
        return result?.toDomainModel()
    }
}
