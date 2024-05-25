package woowacourse.shopping.data.db.recentviewedItem

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.db.recentviewedItem.RecentViewedItemDatabase.Companion.RECENT_VIEWED_ITEM_DB_NAME
import woowacourse.shopping.data.model.RecentViewedItemEntity

@Dao
interface RecentViewedItemDao {
    @Insert
    fun saveRecentViewedItem(recentViewedItemEntity: RecentViewedItemEntity): Long

    @Query("SELECT * FROM $RECENT_VIEWED_ITEM_DB_NAME ORDER BY viewedAt DESC")
    fun findAllItemsByMostRecent(): List<RecentViewedItemEntity>
}
