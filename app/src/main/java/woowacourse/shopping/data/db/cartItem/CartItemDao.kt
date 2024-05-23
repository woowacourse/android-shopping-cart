package woowacourse.shopping.data.db.cartItem

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import woowacourse.shopping.data.db.cartItem.CartItemDatabase.Companion.CART_ITEMS_DB_NAME
import woowacourse.shopping.data.model.CartItemEntity

@Dao
interface CartItemDao {
    @Insert
    fun saveCartItem(cartItemEntity: CartItemEntity): Long

    @Query("SELECT * FROM $CART_ITEMS_DB_NAME")
    fun findAll(): List<CartItemEntity>

    @Query("SELECT * FROM $CART_ITEMS_DB_NAME LIMIT :pagingSize OFFSET :offset")
    fun findPagingCartItem(
        offset: Int,
        pagingSize: Int,
    ): List<CartItemEntity>

    @Query("SELECT * FROM $CART_ITEMS_DB_NAME WHERE id = :itemId")
    fun findCartItemById(itemId: Long): CartItemEntity?

    @Query("DELETE FROM $CART_ITEMS_DB_NAME WHERE id = :itemId")
    fun deleteCartItemById(itemId: Long)

    @Query("SELECT COUNT(*) FROM $CART_ITEMS_DB_NAME")
    fun getItemCount(): Int

    @Query("SELECT * FROM $CART_ITEMS_DB_NAME WHERE productId = :productId")
    fun getCartItemByProductId(productId: Long): CartItemEntity?

    @Update
    fun updateCartItem(cartItemEntity: CartItemEntity)
}
