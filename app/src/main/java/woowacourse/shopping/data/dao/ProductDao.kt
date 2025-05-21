package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.entity.ProductEntity

@Dao
interface ProductDao {
    @Insert
    fun insert(item: ProductEntity)

    @Query("SELECT * FROM product")
    fun getAll(): List<ProductEntity>

    @Query("SELECT COUNT(*) FROM product")
    fun totalSize(): Int

    @Query("DELETE FROM product WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM product ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getPaged(
        offset: Int,
        limit: Int,
    ): List<ProductEntity>
}
