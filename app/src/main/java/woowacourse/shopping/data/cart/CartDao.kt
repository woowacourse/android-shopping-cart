package woowacourse.shopping.data.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_item")
    fun getAll(): List<CartItemEntity>

    @Insert
    fun insert(cartItem: CartItemEntity)
}
