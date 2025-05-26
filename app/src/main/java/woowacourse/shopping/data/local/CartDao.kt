package woowacourse.shopping.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart WHERE id =:id")
    fun findById(id: Long): ProductEntity?

    @Query("SELECT * FROM cart LIMIT :limit OFFSET :offset")
    fun findPagedItems(
        limit: Int,
        offset: Int,
    ): List<ProductEntity>

    @Insert
    fun insert(productEntity: ProductEntity)

    @Insert
    fun insertAll(vararg productEntity: ProductEntity)

    @Delete
    fun delete(productEntity: ProductEntity)

    @Query("DELETE FROM cart WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT COUNT(*) FROM cart ")
    fun size(): Int
}
