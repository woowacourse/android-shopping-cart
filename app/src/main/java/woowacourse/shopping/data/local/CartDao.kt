package woowacourse.shopping.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items ORDER BY rowid ASC")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    fun getCartItem(productId: String): Flow<CartEntity?>

    @Upsert
    fun upsert(cartEntity: CartEntity)
    @Query("DELETE FROM cart_items WHERE productId = :productId")
    fun deleteCartItem(productId: String)

    @Query("SELECT COUNT(*) FROM cart_items")
    fun getCartItemCount(): Int

    @Query("SELECT * FROM cart_items ORDER BY rowid ASC LIMIT :limit OFFSET :offset")
    fun getPagingCartItems(limit: Int, offset: Int): List<CartEntity>
}
