package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM ProductEntity")
    fun getAll(): Boolean

    @Query("SELECT * FROM ProductEntity ORDER BY id DESC Limit :limit OFFSET :offset")
    fun getCartItemPaged(
        limit: Int,
        offset: Int,
    ): List<ProductEntity>

    @Query("DELETE FROM ProductEntity WHERE id = :id")
    fun delete(id: Long)

    @Insert
    fun insert(product: ProductEntity)
}
