package woowacourse.shopping.data.recentProducts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM recent_product ORDER BY viewedTime DESC")
    fun getAll(): List<RecentProductEntity>

    @Query("SELECT count(*) FROM recent_product")
    fun getAllSize(): Int

    @Query("SELECT * FROM recent_product ORDER BY viewedTime ASC LIMIT 1")
    fun getOldest(): RecentProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentProduct: RecentProductEntity)

    @Query("DELETE FROM recent_product WHERE id = :productId")
    fun delete(productId: Long)

    @Query("UPDATE recent_product SET viewedTime = :newTime WHERE id = :productId")
    fun updateViewedTime(
        productId: Long,
        newTime: Long = System.currentTimeMillis(),
    )

    @Query("UPDATE recent_product SET quantity = :newQuantity WHERE id = :productId")
    fun updateQuantity(
        productId: Long,
        newQuantity: Int,
    )

    @Query("SELECT * FROM recent_product ORDER BY viewedTime DESC LIMIT 1 OFFSET 1")
    fun getSecondMostRecentProduct(): RecentProductEntity?

    @Query("SELECT * FROM recent_product ORDER BY viewedTime DESC LIMIT 1")
    fun getMostRecentProduct(): RecentProductEntity?

    @Query("SELECT * FROM recent_product WHERE id = :productId")
    fun findRecentProductById(productId: Long): RecentProductEntity?
}
