package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.entity.CartItemEntity
import woowacourse.shopping.data.local.entity.CartProductRow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getByProductId(productId: String): CartItemEntity?

    @Upsert
    suspend fun upsert(item: CartItemEntity)

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

    @Query(
        """
        UPDATE cart_items
        SET quantity = quantity + :quantityToAdd
        WHERE productId = :productId
    """
    )
    suspend fun increaseQuantity(productId: String, quantityToAdd: Int): Int

    @Query(
        """
        UPDATE cart_items
        SET quantity = quantity - :quantityToRemove
        WHERE productId = :productId
        AND quantity >= :quantityToRemove
    """
    )
    suspend fun decreaseQuantity(
        productId: String,
        quantityToRemove: Int
    ): Int

    @Query(
        """
          DELETE FROM cart_items
          WHERE productId = :productId
            AND quantity = 0
          """
    )
    suspend fun deleteIfZero(productId: String): Int

    @Transaction
    suspend fun decreaseProduct(
        productId: String,
        quantityToRemove: Int
    ): Boolean {
        val updated = decreaseQuantity(productId,quantityToRemove)
        if(updated == 0) return false

        deleteIfZero(productId)
        return true
    }
}


