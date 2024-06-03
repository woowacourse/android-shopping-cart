package woowacourse.shopping.db.recenteProduct

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentlyViewedProductDao {
    @Query("SELECT * FROM recently_viewed_products ORDER BY viewedAt DESC LIMIT 10")
    fun getRecentProducts(): LiveData<List<RecentlyViewedProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: RecentlyViewedProductEntity)

    @Query("DELETE FROM recently_viewed_products WHERE productId = :productId")
    suspend fun deleteProductByProductId(productId: Int)

    @Query("DELETE FROM recently_viewed_products WHERE id NOT IN (SELECT id FROM recently_viewed_products ORDER BY viewedAt DESC LIMIT 10)")
    suspend fun deleteOldestProducts()

    @Query("UPDATE recently_viewed_products SET viewedAt = :viewedAt WHERE productId = :productId")
    fun updateViewedAt(productId: Int, viewedAt: Long)

    @Query("SELECT * FROM recently_viewed_products WHERE productId = :productId LIMIT 1")
    fun getProductById(productId: Int): RecentlyViewedProductEntity?
}
