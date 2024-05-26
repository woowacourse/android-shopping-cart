package woowacourse.shopping.model.data

import android.content.Context
import woowacourse.shopping.model.Product
import kotlin.concurrent.thread

class RecentProductsRepository(application: Context) {
    private val database = AlsongDatabase.getDatabase(application)
    private val recentProductDao: RecentProductDao = database.recentProductDao()

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
