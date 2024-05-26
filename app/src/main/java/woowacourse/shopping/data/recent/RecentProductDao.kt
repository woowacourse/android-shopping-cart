package woowacourse.shopping.data.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecentProductDao {
    @Insert
    fun saveRecentProduct(recentProduct: RecentProductEntity): Long

    @Query("SELECT * FROM recentProduct ORDER BY viewTime DESC LIMIT 1")
    fun getRecentProduct(): RecentProductEntity?

    @Query("SELECT * FROM recentProduct ORDER BY viewTime DESC LIMIT :pageSize")
    fun getRecentProductsByPaging(pageSize: Int): List<RecentProductEntity>

    @Query("UPDATE recentProduct SET viewTime = :viewTime WHERE id = :productId")
    fun updateViewTime(productId: Long, viewTime: Long)
}
