package woowacourse.shopping.storage.room.shoppingcart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingCartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shoppingCartEntity: ShoppingCartEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(shoppingCartEntities: List<ShoppingCartEntity>)

    @Query("DELETE FROM shopping_cart_items WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("DELETE FROM shopping_cart_items WHERE product_id = :productId")
    suspend fun deleteByProductId(productId: Long): Int

    @Query("SELECT product_id FROM shopping_cart_items")
    suspend fun getProductIds(): List<Long>

    @Query("DELETE FROM shopping_cart_items WHERE product_id IN (:productIds)")
    suspend fun deleteByProductIds(productIds: List<Long>): Int

    @Query(
        """
        SELECT c.id AS id,
               s.product_id AS product_id,
               s.title AS title,
               s.price AS price,
               s.image_url AS image_url,
               s.quantity AS quantity
        FROM shopping_cart_items c
        INNER JOIN shopping_items s ON s.product_id = c.product_id
        WHERE s.quantity > 0
        ORDER BY c.id
        """,
    )
    suspend fun getShoppingCartItemRows(): List<ShoppingCartItemRow>
}
