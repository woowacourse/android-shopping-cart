package woowacourse.shopping.data.database.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import woowacourse.shopping.data.model.cart.CartItem
import woowacourse.shopping.data.model.cart.CartedProduct

@Dao
interface CartDao {
    @Upsert
    fun addCartItem(cartItem: CartItem): Long

    @Transaction
    @Query(
        """
        SELECT * FROM cart_item LIMIT :pageSize OFFSET :page * :pageSize
    """,
    )
    fun getCartedProducts(
        page: Int,
        pageSize: Int,
    ): List<CartedProduct>

    @Query("SELECT SUM(quantity) FROM cart_item")
    fun getTotalQuantity(): Int

    @Query("UPDATE cart_item SET quantity = :quantity WHERE id = :cartItemId")
    fun updateQuantity(
        cartItemId: Long,
        quantity: Int,
    )

    @Delete
    fun deleteCartItem(cartItem: CartItem): Int

    @Query("DELETE FROM cart_item")
    fun deleteAll()
}
