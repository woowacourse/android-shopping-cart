package woowacourse.shopping.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartItemDao {
    @Insert
    suspend fun insertCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("SELECT * FROM CartItemEntity WHERE uid = :uid")
    suspend fun getCartItemByUid(uid: Int): CartItem

    @Query("SELECT * FROM CartItemEntity")
    suspend fun getAll(): List<CartItem>

    @Query("UPDATE CartItemEntity SET quantity = :quantity WHERE uid = :uid")
    suspend fun updateQuantity(
        uid: Long,
        quantity: Int,
    )
}
