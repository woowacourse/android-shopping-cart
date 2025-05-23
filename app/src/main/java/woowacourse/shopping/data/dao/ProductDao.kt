package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM product LIMIT :limit OFFSET :offset;\n")
    suspend fun findAll(
        offset: Int,
        limit: Int,
    ): List<ProductEntity>

    @Query("SELECT COUNT(*) FROM product;")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(productEntity: ProductEntity)
}
