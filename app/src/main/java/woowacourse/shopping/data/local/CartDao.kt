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
    suspend fun upsert(cartEntity: CartEntity)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteCartItem(productId: String)

    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun getCartItemCount(): Int

    @Query("SELECT * FROM cart_items ORDER BY rowid ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagingCartItems(limit: Int, offset: Int): List<CartEntity>
}
