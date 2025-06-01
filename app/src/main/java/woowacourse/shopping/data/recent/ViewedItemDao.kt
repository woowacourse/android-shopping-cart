package woowacourse.shopping.data.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ViewedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertViewedProduct(viewedItem: ViewedItem)

    @Query("SELECT * FROM ViewedItemEntity ORDER BY viewedAt DESC LIMIT 10")
    fun getRecentViewedItems(): List<ViewedItem>

    @Query("SELECT * FROM ViewedItemEntity ORDER BY viewedAt DESC LIMIT 1")
    fun getLastViewedItem(): ViewedItem?
}
