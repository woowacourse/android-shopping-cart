package woowacourse.shopping.data.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cart_item")
    fun findAll(): List<CartItemEntity>

    @Query("SELECT * FROM cart_item WHERE id =:id")
    fun findById(id: Long): CartItemEntity

    @Insert
    fun insert(cartItem: CartItemEntity)

    @Insert
    fun insertAll(vararg cartItem: CartItemEntity)

    @Delete
    fun delete(cartItem: CartItemEntity)

    @Update
    fun update(cartItem: CartItemEntity)
}
