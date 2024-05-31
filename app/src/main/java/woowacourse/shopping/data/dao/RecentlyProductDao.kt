package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.model.RecentlyViewedProductEntity

@Dao
interface RecentlyProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentlyViewedProduct(product: RecentlyViewedProductEntity)

    @Query("SELECT * FROM recently_viewed_products WHERE productId = :productId")
    fun findRecentlyViewedProductById(productId: Long): RecentlyViewedProductEntity?

    @Query("SELECT * FROM recently_viewed_products ORDER BY viewedAt DESC LIMIT :limit")
    fun getRecentlyViewedProducts(limit: Int): List<RecentlyViewedProductEntity>
}
