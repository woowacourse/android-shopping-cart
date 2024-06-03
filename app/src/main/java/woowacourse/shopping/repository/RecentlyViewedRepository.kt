package woowacourse.shopping.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import woowacourse.shopping.db.CartDatabase
import woowacourse.shopping.db.recenteProduct.RecentlyViewedProductEntity

class RecentlyViewedRepository(context: Context) {
    private val database = CartDatabase.getDatabase(context)
    private val recentlyViewedProductDao = database.recentlyViewedProductDao()

    fun getRecentProducts(excludeProductId: Int? = null): LiveData<List<RecentlyViewedProductEntity>> {
        val recentProducts = MutableLiveData<List<RecentlyViewedProductEntity>>()
        recentlyViewedProductDao.getRecentProducts().observeForever { products ->
            val filteredProducts = products.filter { it.productId != excludeProductId }
            recentProducts.value = filteredProducts
        }
        return recentProducts
    }

    suspend fun addProduct(productId: Int) {
        withContext(Dispatchers.IO) {
            val currentTime = System.currentTimeMillis()
            val existingProduct = recentlyViewedProductDao.getProductById(productId)
            if (existingProduct != null) {
                recentlyViewedProductDao.updateViewedAt(productId, currentTime)
            } else {
                val product = RecentlyViewedProductEntity(productId = productId, viewedAt = currentTime)
                recentlyViewedProductDao.insertProduct(product)
                recentlyViewedProductDao.deleteOldestProducts()
            }
        }
    }
}
