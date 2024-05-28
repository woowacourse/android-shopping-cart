package woowacourse.shopping.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.local.model.ProductHistoryEntity

@Dao
interface ProductHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductHistory(productHistoryEntity: ProductHistoryEntity)

    @Query("SELECT * FROM productHistoryEntity WHERE productId = :productId")
    fun findProductHistory(productId: Long): ProductHistoryEntity

    @Query("SELECT * FROM productHistoryEntity ORDER BY createAt DESC LIMIT :size")
    fun getProductHistoryPaged(size: Int): List<ProductHistoryEntity>

    @Query("DELETE FROM productHistoryEntity WHERE productId = :productId")
    fun deleteProductHistory(productId: Long)

    @Query("DELETE FROM productHistoryEntity")
    fun deleteAllProductHistory()
}
