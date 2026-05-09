package woowacourse.shopping.local.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(cartItemEntity: CartItemEntity)

    @Query(
        """
        SELECT * FROM cart_items
        ORDER BY createdAtMillis ASC, productId ASC
        LIMIT :limit OFFSET :fromIndex
        """,
    )
    suspend fun getCartItems(
        fromIndex: Int,
        limit: Int,
    ): List<CartItemEntity>

    @Query(
        """
        SELECT * FROM cart_items
        WHERE productId IN (:productIds)
        ORDER BY createdAtMillis ASC, productId ASC
        """,
    )
    suspend fun getCartItemsByProductIds(productIds: Set<String>): List<CartItemEntity>

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun findByProductId(productId: String): CartItemEntity?

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteByProductId(productId: String)

    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun count(): Int
}
