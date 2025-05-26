package woowacourse.shopping.data.product.dao

import androidx.room.Dao
import androidx.room.Query
import woowacourse.shopping.data.product.entity.ProductEntity

@Dao
interface RecentViewedProductDao {
    @Query("SELECT * FROM products")
    fun load(): List<ProductEntity>
}
