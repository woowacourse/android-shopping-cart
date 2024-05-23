package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.model.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products WHERE id = :productId")
    fun findWithProductId(productId: Long): ProductEntity

    @Query("SELECT * FROM products LIMIT :limit OFFSET :offset")
    fun findByPaged(
        offset: Int,
        limit: Int,
    ): List<ProductEntity>
}
