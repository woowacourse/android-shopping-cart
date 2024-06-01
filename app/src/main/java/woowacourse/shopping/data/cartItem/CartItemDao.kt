package woowacourse.shopping.data.cartItem

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import woowacourse.shopping.data.cartItem.CartItemDatabase.Companion.CART_ITEMS_DB_NAME

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

    @Query("SELECT SUM(quantity) FROM $CART_ITEMS_DB_NAME")
    fun getTotalQuantity(): Int
}
