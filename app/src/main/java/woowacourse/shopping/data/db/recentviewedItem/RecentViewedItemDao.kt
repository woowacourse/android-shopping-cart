package woowacourse.shopping.data.db.recentviewedItem

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.db.recentviewedItem.RecentViewedItemDatabase.Companion.RECENT_VIEWED_ITEM_DB_NAME
import woowacourse.shopping.data.model.RecentViewedItemEntity
import java.time.LocalDateTime

@Dao
interface RecentViewedItemDao {
    @Insert
    fun saveRecentViewedItem(recentViewedItemEntity: RecentViewedItemEntity): Long

    @Query("SELECT * FROM $RECENT_VIEWED_ITEM_DB_NAME ORDER BY viewedAt DESC")
    fun findAllItemsByMostRecent(): List<RecentViewedItemEntity>

    @Query("SELECT * FROM $RECENT_VIEWED_ITEM_DB_NAME ORDER BY viewedAt DESC LIMIT 1")
    fun findItemByMostRecent(): RecentViewedItemEntity?

    @Query("SELECT * FROM $RECENT_VIEWED_ITEM_DB_NAME WHERE productId = :productId")
    fun findItemByProductId(productId: Long): RecentViewedItemEntity?

    @Query("UPDATE $RECENT_VIEWED_ITEM_DB_NAME SET viewedAt = :newViewedAt WHERE productId = :productId")
    fun updateViewedDate(
        productId: Long,
        newViewedAt: LocalDateTime,
    )
}
