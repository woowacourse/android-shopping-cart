package woowacourse.shopping.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.CartedProduct

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_item LIMIT :pageSize OFFSET :page * :pageSize")
    fun getCartItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    @Query("SELECT * FROM cart_item WHERE id=:cartItemId")
    fun getCartItem(cartItemId: Long): CartItem

    @Query("SELECT SUM(quantity) FROM cart_item")
    fun getTotalQuantity(): Int

    @Upsert
    fun addCartItem(cartItem: CartItem): Long

    @Query("UPDATE cart_item SET quantity = :quantity WHERE id = :cartItemId")
    fun updateQuantity(cartItemId: Long, quantity: Int)

    @Delete
    fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_item")
    fun deleteAll()

    @Transaction
    @Query("""
        SELECT * FROM cart_item LIMIT :pageSize OFFSET :page * :pageSize
    """)
    fun getCartedProducts(
        page: Int,
        pageSize: Int,
    ): List<CartedProduct>
}
