package woowacourse.shopping.data.database.history

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct

@Dao
interface ProductHistoryDao {
    @Transaction
    @Query(
        """
        SELECT * FROM product_history
        GROUP BY product_history.productId
        ORDER BY product_history.id DESC
        LIMIT :pageSize
    """,
    )
    fun getProductHistory(pageSize: Int): List<RecentProduct>

    @Query("DELETE FROM product_history WHERE productId=:productId")
    fun deleteProductHistory(productId: Long)

    @Upsert
    fun insertProductHistory(productHistory: ProductHistory): Long

    @Transaction
    fun addProductHistory(productHistory: ProductHistory) {
        deleteProductHistory(productHistory.productId)
        insertProductHistory(productHistory)
    }

    @Query("SELECT * FROM product_history ORDER BY id DESC LIMIT 1")
    fun getLastProduct(): RecentProduct?
}
