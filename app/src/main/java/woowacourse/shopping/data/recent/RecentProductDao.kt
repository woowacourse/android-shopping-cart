package woowacourse.shopping.data.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.recent.RecentProductEntity.Companion.RECENT_PRODUCT_TABLE_NAME
import woowacourse.shopping.domain.RecentProduct

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM $RECENT_PRODUCT_TABLE_NAME ORDER BY timestamp DESC LIMIT :count")
    fun getMostRecent(count: Int): List<RecentProduct>

    @Query("SELECT * FROM $RECENT_PRODUCT_TABLE_NAME WHERE id != :id ORDER BY timestamp DESC LIMIT 1")
    fun getLastProductBefore(id: Int): RecentProduct?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recentProductEntity: RecentProductEntity)

    @Query("DELETE FROM $RECENT_PRODUCT_TABLE_NAME")
    fun clear()
}
