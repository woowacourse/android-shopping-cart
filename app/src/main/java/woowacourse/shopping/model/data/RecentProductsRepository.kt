package woowacourse.shopping.model.data

import woowacourse.shopping.model.Product
import kotlin.concurrent.thread

class RecentProductsRepository(private val recentProductDao: RecentProductDao) {
    fun getAllData(): List<Product> {
        var recentProductsEntity: List<RecentProductEntity> = emptyList()
        thread {
            recentProductsEntity = recentProductDao.getAll()
        }.join()
        val recentProducts =
            recentProductsEntity.map {
                ProductsImpl.find(it.productId)
            }
        return recentProducts
    }

    fun getSecondLastData(): Product? {
        var lastProductEntity: RecentProductEntity? = null
        thread {
            lastProductEntity = recentProductDao.getSecondLast()
        }.join()
        return lastProductEntity?.let {
            ProductsImpl.find(it.productId)
        }
    }

    fun insert(entity: RecentProductEntity) {
        thread {
            recentProductDao.insert(entity)
        }.join()
    }
}
