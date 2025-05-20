package woowacourse.shopping.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAll(): List<CartEntity>

    @Insert
    fun insertAll(vararg cartEntities: CartEntity)

    @Delete
    fun delete(cartEntity: CartEntity)

    @Query("SELECT * FROM cart LIMIT :limit OFFSET :offset")
    fun getPage(
        limit: Int,
        offset: Int,
    ): List<CartEntity>

    @Query("SELECT COUNT(*) FROM cart")
    fun getAllItemsSize(): Int
}
