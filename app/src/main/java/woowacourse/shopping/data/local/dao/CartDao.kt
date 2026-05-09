package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.local.entity.CartItemEntity

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    suspend fun findAll(): List<CartItemEntity>

    @Query("SELECT * FROM cart_items LIMIT :limit OFFSET :offset")
    suspend fun findPagingItems(
        limit: Int,
        offset: Int,
    ): List<CartItemEntity>

    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun findByProductId(productId: String): CartItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteByProductId(productId: String)

    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun countItems(): Int

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM cart_items")
    suspend fun sumQuantity(): Int
}
