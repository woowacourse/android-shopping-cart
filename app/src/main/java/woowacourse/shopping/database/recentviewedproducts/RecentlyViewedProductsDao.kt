package woowacourse.shopping.database.recentviewedproducts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.time.LocalDateTime

@Dao
interface RecentlyViewedProductsDao {
    @Query("SELECT * FROM recently_viewed_products ORDER BY viewed_time DESC LIMIT 10")
    fun getRecentTenProducts(): List<RecentlyViewedProductEntity>?

    @Query("SELECT * FROM recently_viewed_products ORDER BY viewed_time DESC LIMIT 1")
    fun getLastViewedProduct(): RecentlyViewedProductEntity?

    @Query("SELECT * FROM recently_viewed_products WHERE id = :id")
    fun findProductById(id: Long): RecentlyViewedProductEntity?

    @Insert
    fun insertRecentlyViewedProduct(recentlyViewedProductEntity: RecentlyViewedProductEntity)

    @Query("UPDATE recently_viewed_products SET viewed_time = :viewedTime WHERE id = :id")
    fun updateViewedTime(
        id: Long,
        viewedTime: LocalDateTime,
    )
}
