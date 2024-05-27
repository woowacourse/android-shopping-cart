package woowacourse.shopping.data.database.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.model.RecentProductEntity

@Dao
interface RecentProductDao {
    @Insert
    fun save(recentProductEntity: RecentProductEntity)

    @Query("SELECT * FROM recent_products ORDER BY id DESC LIMIT 1")
    fun loadLatestData(): RecentProductEntity?

    @Query("SELECT * FROM recent_products DISTICT ORDER BY id DESC LIMIT 10")
    fun loadData(): List<RecentProductEntity>

    @Query("DELETE FROM recent_products WHERE productId = :productId")
    fun deleteWithProductId(productId: Long)
}
