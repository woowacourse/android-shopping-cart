package woowacourse.shopping.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.ProductHistory
import woowacourse.shopping.data.model.RecentProduct

@Dao
interface ProductHistoryDao {
    @Upsert
    fun insertProductHistory(productHistory: ProductHistory): Long

    @Transaction
    @Query("""
        SELECT * FROM product_history
        GROUP BY product_history.productId
        ORDER BY product_history.id DESC
        LIMIT :pageSize
    """)
    fun getProductHistory(
        pageSize: Int,
    ): List<RecentProduct>
}
