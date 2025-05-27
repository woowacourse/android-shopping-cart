package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM product LIMIT :limit OFFSET :offset;\n")
    fun findAll(
        offset: Int,
        limit: Int,
    ): List<ProductEntity>

    @Query("SELECT COUNT(*) FROM product;")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(productEntity: ProductEntity)
}
