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

    @Query("SELECT * FROM $CART_ITEMS_DB_NAME LIMIT :pagingSize OFFSET :offset")
    fun findPagingCartItem(
        offset: Int,
        pagingSize: Int,
    ): List<CartItemEntity>

    @Query("SELECT * FROM $CART_ITEMS_DB_NAME WHERE id = :itemId")
    fun findCartItemById(itemId: Long): CartItemEntity?

    @Query("DELETE FROM $CART_ITEMS_DB_NAME WHERE id = :itemId")
    fun deleteCartItemById(itemId: Long): Int

    @Query("UPDATE $CART_ITEMS_DB_NAME SET count = :count WHERE id = :itemId")
    fun updateCartItemCount(
        itemId: Long,
        count: Int,
    ): Int

    @Query("SELECT * FROM $CART_ITEMS_DB_NAME WHERE productId = :productId")
    fun findCartItemByProductId(productId: Long): CartItemEntity?

    @Query("SELECT SUM(count) FROM $CART_ITEMS_DB_NAME")
    fun getTotalItemCount(): Int
}
