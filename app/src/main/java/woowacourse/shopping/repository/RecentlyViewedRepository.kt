package woowacourse.shopping.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import woowacourse.shopping.db.CartDatabase
import woowacourse.shopping.db.recenteProduct.RecentlyViewedProductEntity

class RecentlyViewedRepository(context: Context) {
    private val database = CartDatabase.getDatabase(context)
    private val recentlyViewedProductDao = database.recentlyViewedProductDao()

    suspend fun getRecentProducts() = withContext(Dispatchers.IO) {
        recentlyViewedProductDao.getRecentProducts()
    }

    suspend fun addProduct(productId: Int) = withContext(Dispatchers.IO) {
        val currentTime = System.currentTimeMillis()
        // 중복 제거를 위해 기존의 동일 productId를 가진 항목을 삭제
        recentlyViewedProductDao.deleteProductByProductId(productId)
        // 새로 추가
        val product = RecentlyViewedProductEntity(productId = productId, viewedAt = currentTime)
        recentlyViewedProductDao.insertProduct(product)
        // 10개 초과 시 가장 오래된 항목 삭제
        recentlyViewedProductDao.deleteOldestProducts()
    }

    suspend fun deleteProductByProductId(productId: Int) = withContext(Dispatchers.IO) {
        recentlyViewedProductDao.deleteProductByProductId(productId)
    }
}
