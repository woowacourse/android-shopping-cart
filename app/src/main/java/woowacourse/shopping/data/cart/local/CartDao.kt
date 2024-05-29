package woowacourse.shopping.data.cart.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAll(): List<CartItemEntity>

    @Insert
    fun insert(cartItemEntity: CartItemEntity)

    @Delete
    fun delete(cartItemEntity: CartItemEntity)

    @Query("DELETE FROM cart")
    fun drop()
}
