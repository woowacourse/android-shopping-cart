package woowacourse.shopping.model.data

import android.content.Context
import android.util.Log
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
        Log.d("alsong", "$recentProducts")
        return recentProducts
    }

    fun insert(entity: RecentProductEntity) {
        thread {
            recentProductDao.insert(entity)
        }.join()
    }
}
