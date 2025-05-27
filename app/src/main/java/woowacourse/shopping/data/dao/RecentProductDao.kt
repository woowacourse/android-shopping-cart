package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.shopping.data.entity.RecentProductEntity

@Dao
interface RecentProductDao {
    @Insert
    fun insert(recentProductEntity: RecentProductEntity)

    @Query("SELECT * FROM recent_product ORDER BY viewed_at DESC LIMIT :limit OFFSET :offset")
    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): List<RecentProductEntity>

    @Query("SELECT * FROM recent_product ORDER BY viewed_at DESC LIMIT 1")
    fun getLastViewedProduct(): RecentProductEntity?

    @Query("DELETE from recent_product WHERE product_id == :productId")
    fun deleteByProductId(productId: Long)

    @Transaction
    fun replaceRecentProduct(recentProductEntity: RecentProductEntity) {
        deleteByProductId(recentProductEntity.productId)
        insert(recentProductEntity)
    }
}
