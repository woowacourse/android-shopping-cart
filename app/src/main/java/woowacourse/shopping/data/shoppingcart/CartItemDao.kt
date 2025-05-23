package woowacourse.shopping.data.shoppingcart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.shoppingcart.CartItemEntity.Companion.CART_ITEM_TABLE_NAME

@Dao
interface CartItemDao {
    @Query("SELECT * FROM $CART_ITEM_TABLE_NAME WHERE id == :id")
    fun getOrNull(id: Int): CartItemEntity?

    @Query("SELECT * FROM $CART_ITEM_TABLE_NAME")
    fun getAll(): List<CartItemEntity>

    @Query("DELETE FROM $CART_ITEM_TABLE_NAME")
    fun clear()

    @Insert
    fun insert(cartItem: CartItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cartItems: List<CartItemEntity>)
}
