package woowacourse.shopping.repository.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.shopping.repository.entity.ShoppingCartItemEntity

@Dao
interface ShoppingCartItemDao {
    @Query("INSERT INTO shopping_cart_items (product_id, quantity) VALUES (:productId, :quantity)")
    suspend fun addShoppingCartItem(
        productId: String,
        quantity: Int,
    )

    @Query("UPDATE shopping_cart_items SET quantity = quantity + :quantity WHERE id = :shoppingCartItemId")
    suspend fun increaseQuantity(
        shoppingCartItemId: String,
        quantity: Int,
    )

    @Query("SELECT * FROM shopping_cart_items WHERE id = :shoppingCartItemId")
    suspend fun getShoppingCartItemEntity(shoppingCartItemId: String): ShoppingCartItemEntity?

    @Query("SELECT id FROM shopping_cart_items WHERE product_id = :productId")
    suspend fun getShoppingCartItemId(productId: String): Int?

    @Transaction
    suspend fun addOrIncreaseCartItem(
        productId: String,
        quantity: Int,
    ) {
        val shoppingCartItemId = getShoppingCartItemId(productId)
        if (shoppingCartItemId == null) {
            addShoppingCartItem(productId, quantity)
            return
        }
        increaseQuantity(shoppingCartItemId.toString(), quantity)
    }

    @Query("SELECT * FROM shopping_cart_items LIMIT :size OFFSET :offset")
    suspend fun getShoppingCartItemEntities(
        offset: Int,
        size: Int,
    ): List<ShoppingCartItemEntity>

    @Query("DELETE FROM shopping_cart_items WHERE id = :shoppingCartItemId AND quantity <= 0")
    suspend fun removeCartItemIfQuantityIsZeroOrLess(shoppingCartItemId: String)

    @Query("UPDATE shopping_cart_items SET quantity = quantity - :quantity WHERE id = :shoppingCartItemId")
    suspend fun decreaseQuantity(
        shoppingCartItemId: String,
        quantity: Int,
    )

    @Transaction
    suspend fun removeOrDecreaseCartItem(
        shoppingCartItemId: String,
        quantity: Int,
    ) {
        decreaseQuantity(shoppingCartItemId, quantity)
        removeCartItemIfQuantityIsZeroOrLess(shoppingCartItemId)
    }

    @Query("SELECT COUNT(*) FROM shopping_cart_items")
    suspend fun getTotalSize(): Int
}
