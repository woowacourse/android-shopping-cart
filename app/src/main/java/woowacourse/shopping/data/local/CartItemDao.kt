package woowacourse.shopping.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cart_item")
    fun getAll(): List<CartItemEntity>

    @Query("SELECT * FROM cart_item WHERE id =:id")
    fun findById(id: Long): CartItemEntity

    @Insert
    fun insert(cartItem: CartItemEntity)

    @Insert
    fun insertAll(vararg cartItem: CartItemEntity)

    @Delete
    fun delete(cartItem: CartItemEntity)

    @Query("DELETE FROM cart WHERE id = :id")
    fun deleteById(id: Long)

    @Update
    fun update(cartItem: CartItemEntity)
}
