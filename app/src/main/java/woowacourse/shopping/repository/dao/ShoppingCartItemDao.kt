package woowacourse.shopping.repository.dao

import androidx.room.Dao
import androidx.room.Query
import woowacourse.shopping.repository.entity.ShoppingCartItemEntity

@Dao
interface ShoppingCartItemDao {
    @Query("INSERT INTO shopping_cart_items (product_id, quantity) VALUES (:productId, :quantity)")
    suspend fun addCartItemByProductId(
        productId: String,
        quantity: Int,
    )

    @Query("SELECT * FROM shopping_cart_items WHERE product_id = :productId")
    suspend fun getItemByProductId(productId: String): ShoppingCartItemEntity?

    @Query("SELECT * FROM shopping_cart_items LIMIT :size OFFSET :offset")
    suspend fun getItems(
        offset: Int,
        size: Int,
    ): List<ShoppingCartItemEntity>

    @Query("DELETE FROM shopping_cart_items WHERE product_id = :productId")
    suspend fun removeItem(productId: String)

    @Query("DELETE FROM shopping_cart_items WHERE product_id = :productId AND quantity <= 0")
    suspend fun removeItemByProductIdIfQuantityIsZeroOrLess(productId: String)

    @Query("UPDATE shopping_cart_items SET quantity = quantity + :quantity WHERE product_id = :productId")
    suspend fun changeItemQuantity(
        productId: String,
        quantity: Int,
    )

    @Query("SELECT COUNT(*) FROM shopping_cart_items")
    suspend fun getTotalSize(): Int

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM shopping_cart_items")
    suspend fun getTotalQuantity(): Int
}
