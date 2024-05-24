package woowacourse.shopping.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import woowacourse.shopping.db.model.CartItemEntity

@Dao
interface CartItemDao {

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): List<CartItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItemEntity)

    @Update
    suspend fun update(cartItem: CartItemEntity)

    @Delete
    suspend fun delete(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun getCartItemByProductId(productId: Int): CartItemEntity?
}
