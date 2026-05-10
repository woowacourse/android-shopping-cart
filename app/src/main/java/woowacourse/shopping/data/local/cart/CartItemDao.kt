package woowacourse.shopping.data.local.cart

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {
    @Query(
        """
        SELECT * FROM cart_items
        ORDER BY productId
        """,
    )
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Query(
        """
        SELECT * FROM cart_items
        WHERE productId = :productId
        LIMIT 1
        """,
    )
    suspend fun getCartItem(productId: String): CartItemEntity?

    @Upsert
    suspend fun upsert(cartItem: CartItemEntity)

    @Query(
        """
        DELETE FROM cart_items
        WHERE productId = :productId
        """,
    )
    suspend fun delete(productId: String)
}
