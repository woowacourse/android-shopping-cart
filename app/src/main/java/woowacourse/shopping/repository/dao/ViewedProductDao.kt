package woowacourse.shopping.repository.dao

import androidx.room.Dao
import androidx.room.Query
import woowacourse.shopping.repository.entity.ViewedProductEntity

@Dao
interface ViewedProductDao {
    @Query("INSERT OR REPLACE INTO viewed_products (product_id, viewed_at) VALUES (:productId, :viewedAt)")
    suspend fun addViewedProductByProductId(
        productId: String,
        viewedAt: Long,
    )

    @Query("SELECT * FROM viewed_products ORDER BY viewed_at DESC LIMIT :size OFFSET :offset")
    suspend fun getViewedProducts(
        offset: Int,
        size: Int,
    ): List<ViewedProductEntity>
}
