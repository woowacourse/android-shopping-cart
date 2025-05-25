package woowacourse.shopping.data.recent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ViewedItemDao {
    @Insert
    fun insertViewedProduct(viewedItem: ViewedItem)

    @Query("DELETE FROM ViewedItemEntity WHERE id = :id")
    fun deleteByProductId(id: Long)

    @Query("SELECT * FROM ViewedItemEntity WHERE id = :id")
    fun getViewedProductById(id: Long): ViewedItem?

    @Query("SELECT * FROM ViewedItemEntity")
    fun getAll(): List<ViewedItem>

    @Query("SELECT EXISTS(SELECT * FROM ViewedItemEntity WHERE id = :id)")
    fun isViewedProductExist(id: Long): Boolean
}
