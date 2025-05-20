package woowacourse.shopping.data.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM products LIMIT :limit OFFSET :offset")
    fun findInRange(
        limit: Int,
        offset: Int,
    ): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    fun findById(id: Long): ProductEntity

    @Insert
    fun insertAll(vararg productEntity: ProductEntity)
}
