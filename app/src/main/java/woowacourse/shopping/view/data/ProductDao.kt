package woowacourse.shopping.view.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.product.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun load(): List<ProductEntity>

    @Insert
    fun insertAll(products: List<ProductEntity>)
}
