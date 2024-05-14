package woowacourse.shopping.data.db.cartItem

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.db.cartItem.CartItemDatabase.Companion.CART_ITEMS_DB_NAME
import woowacourse.shopping.data.model.CartItemEntity

@Dao
interface CartItemDao {
    @Insert
    fun saveCartItem(cartItemEntity: CartItemEntity): Long

    @Query("SELECT * FROM $CART_ITEMS_DB_NAME")
    fun findAll(): List<CartItemEntity>

    @Query("SELECT * FROM $CART_ITEMS_DB_NAME WHERE id = :itemId")
    fun findCartItemById(itemId: Long): CartItemEntity?

    @Query("DELETE FROM $CART_ITEMS_DB_NAME WHERE id = :itemId")
    fun deleteCartItemById(itemId: Long)

}
