package woowacourse.shopping.model.data

import android.content.Context
import kotlin.concurrent.thread

class RecentProductsRepository(application: Context) {
    private val database = RecentProductDatabase.getDatabase(application)
    private val recentProductDao: RecentProductDao = database.recentProductDao()

    fun getAllData(): List<RecentProductEntity> {
        var recentProduct: List<RecentProductEntity> = emptyList()
        thread {
            recentProduct = recentProductDao.getAll()
        }.join()
        return recentProduct
    }

    fun insert(entity: RecentProductEntity) {
        thread {
            recentProductDao.insert(entity)
        }.join()
    }
}
