package woowacourse.shopping.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.db.model.ProductBrowsingHistoryEntity

@Dao
interface ProductBrowsingHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putHistory(productBrowsingHistoryEntity: ProductBrowsingHistoryEntity)

    @Query("select * from ProductBrowsingHistoryEntity order by timeStamp desc limit :size")
    fun getHistories(size: Int): List<ProductBrowsingHistoryEntity>
}
