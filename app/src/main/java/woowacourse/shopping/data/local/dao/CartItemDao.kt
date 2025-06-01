package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import woowacourse.shopping.data.local.entity.CartItemEntity

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cart_item")
    fun getAll(): List<CartItemEntity>

    @Query("SELECT * FROM cart_item WHERE id =:id")
    fun findById(id: Long): CartItemEntity?

    @Query("SELECT * FROM cart_item LIMIT :limit OFFSET :offset")
    fun findPagedItems(
        limit: Int,
        offset: Int,
    ): List<CartItemEntity>

    @Query("SELECT COUNT(*) FROM cart_item ")
    fun size(): Int

    @Upsert
    fun upsert(cartItem: CartItemEntity)

    @Insert
    fun insertAll(vararg cartItem: CartItemEntity)

    @Delete
    fun delete(cartItem: CartItemEntity)

    @Query("DELETE FROM cart WHERE id = :id")
    fun deleteById(id: Long)

    @Update
    fun update(cartItem: CartItemEntity)
}
