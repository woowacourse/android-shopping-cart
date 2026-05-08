package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.data.local.entity.CartProductRow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getByProductId(productId: String): CartItemEntity?

    @Insert
    suspend fun insert(item: CartItemEntity)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(
        productId: String,
        quantity: Int,
    )

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteByProductId(productId: String)

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM cart_items")
    suspend fun getTotalQuantity(): Int

    @Query(
        """
          SELECT
              p.productId AS productId,
              p.product_name AS productName,
              p.image_url AS imageUrl,
              p.price AS price,
              c.quantity AS quantity
          FROM cart_items c
          JOIN products p ON p.productId = c.productId
      """,
    )
    fun getCartProducts(): Flow<List<CartProductRow>>
}
