package woowacourse.shopping.data.product.dao

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

    @Query("SELECT * FROM products WHERE id = :id")
    fun load(id: Long): ProductEntity?
}
