package woowacourse.shopping.data.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartItemDao {
    @Insert
    fun insertCartItem(cartItem: CartItem)

    @Query("DELETE FROM CartItemEntity WHERE id = :id")
    fun deleteByProductId(id: Long)

    @Query("SELECT * FROM CartItemEntity WHERE id = :id")
    fun getCartItemById(id: Long): CartItem?

    @Query("SELECT * FROM CartItemEntity")
    fun getAll(): List<CartItem>

    @Query("UPDATE CartItemEntity SET quantity = :quantity WHERE id = :id")
    fun updateQuantity(
        id: Long,
        quantity: Int,
    )

    @Query("SELECT EXISTS(SELECT * FROM CartItemEntity WHERE id = :id)")
    fun isCartItemExist(id: Long): Boolean
}
