package woowacourse.shopping.data.db.recently

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.db.recently.RecentlyProductDatabase.Companion.RECENTLY_ITEM_DB_NAME
import woowacourse.shopping.data.model.RecentlyProductEntity

@Dao
interface RecentlyProductDao {
    @Insert
    fun addRecentlyProduct(recentlyProduct: RecentlyProductEntity): Long

    @Query("SELECT * FROM $RECENTLY_ITEM_DB_NAME ORDER BY id DESC LIMIT 1")
    fun getMostRecentlyProduct(): RecentlyProductEntity?

    @Query("SELECT * FROM $RECENTLY_ITEM_DB_NAME ORDER BY id DESC LIMIT :pagingSize")
    fun findPagingRecentlyProduct(pagingSize: Int): List<RecentlyProductEntity>

    @Query("DELETE FROM $RECENTLY_ITEM_DB_NAME WHERE id = :id")
    fun deleteRecentlyProductById(id: Long): Int
}
