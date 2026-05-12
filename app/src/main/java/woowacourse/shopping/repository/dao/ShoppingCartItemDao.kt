package woowacourse.shopping.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import woowacourse.shopping.repository.entity.ShoppingCartItemEntity

@Dao
interface ShoppingCartItemDao {
    @Insert
    suspend fun addCartItem(shoppingCartItemEntity: ShoppingCartItemEntity)

    @Update
    suspend fun updateCartItem(shoppingCartItemEntity: ShoppingCartItemEntity)

    @Query("SELECT * FROM shopping_cart_items LIMIT :size OFFSET :offset")
    suspend fun getItems(
        offset: Int,
        size: Int,
    ): List<ShoppingCartItemEntity>

    @Query("SELECT * FROM shopping_cart_items WHERE product_id = :productId")
    suspend fun getCartItem(productId: String): ShoppingCartItemEntity?

    @Query("DELETE FROM shopping_cart_items WHERE product_id = :productId")
    suspend fun removeCartItem(productId: String)

    @Query("SELECT COUNT(*) FROM shopping_cart_items")
    suspend fun getTotalSize(): Int

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM shopping_cart_items")
    suspend fun getTotalQuantity(): Int
}
