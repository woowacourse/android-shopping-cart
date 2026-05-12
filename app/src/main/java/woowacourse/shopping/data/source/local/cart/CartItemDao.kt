package woowacourse.shopping.data.source.local.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cartItems LIMIT :count OFFSET :offset")
    suspend fun getCartItems(
        offset: Int,
        count: Int,
    ): List<CartItemEntity>

    @Insert
    suspend fun insert(cartItem: CartItemEntity)

    @Update
    suspend fun update(cartItem: CartItemEntity)

    @Query("DELETE FROM cartItems WHERE product_id = :productId")
    suspend fun delete(productId: String)

    @Query("SELECT * FROM cartItems WHERE product_id = :productId")
    suspend fun getCartItemById(productId: String): CartItemEntity?

    @Query("SELECT SUM(quantity) From cartItems ")
    suspend fun getTotalCount(): Int

    @Query("SELECT COUNT(*) From cartItems ")
    suspend fun getTotalItemCount(): Int
}
