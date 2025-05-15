package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT EXISTS(SELECT 1 FROM ProductEntity WHERE id > :id)")
    fun existsItemAfterId(id: Long): Boolean

    @Query("SELECT * FROM ProductEntity ORDER BY id ASC Limit :limit OFFSET :offset")
    fun getCartItemPaged(
        limit: Int,
        offset: Int,
    ): List<ProductEntity>

    @Query("DELETE FROM ProductEntity WHERE id = :id")
    fun delete(id: Long)

    @Insert
    fun insert(product: ProductEntity)
}
