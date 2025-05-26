package woowacourse.shopping.data.recentProducts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recent_product")
    fun getAll(): List<RecentProductEntity>

    @Insert
    fun insert(recentProduct: RecentProductEntity)

    @Query("DELETE FROM recent_product WHERE id = :productId")
    fun delete(productId: Long)

    @Query("UPDATE recent_product SET viewedTime = :newTime WHERE id = :productId")
    fun updateViewedTime(
        productId: Long,
        newTime: Long = System.currentTimeMillis(),
    )

    @Query("SELECT * FROM recent_product ORDER BY viewedTime DESC LIMIT 1")
    fun getMostRecentProduct(): RecentProductEntity?

    @Query("SELECT * FROM recent_product WHERE id = :productId")
    fun findRecentProductById(productId: Long): RecentProductEntity?
}
