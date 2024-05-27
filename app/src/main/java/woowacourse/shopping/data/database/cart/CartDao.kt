package woowacourse.shopping.data.database.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.model.CartItemEntity

@Dao
interface CartDao {
    @Insert
    fun save(cartItemEntity: CartItemEntity)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId")
    fun update(
        productId: Long,
        quantity: Int,
    )

    @Query("SELECT quantity FROM cart_items WHERE productId = :productId")
    fun findQuantityWithProductId(productId: Long): Int

    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :itemId")
    fun updateQuantity(
        itemId: Long,
        quantity: Int,
    )

    @Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId")
    fun updateQuantityWithProductId(
        productId: Long,
        quantity: Int,
    )

    @Query("SELECT COUNT(*) FROM cart_items")
    fun size(): Int

    @Query("SELECT SUM(quantity) FROM cart_items")
    fun sumOfQuantity(): Int

    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    fun findWithProductId(productId: Long): CartItemEntity

    @Query("SELECT * FROM cart_items WHERE id = :id")
    fun find(id: Long): CartItemEntity

    @Query("SELECT * FROM cart_items")
    fun findAll(): List<CartItemEntity>

    @Query("SELECT * FROM cart_items LIMIT :limit OFFSET :offset")
    fun findAllPaged(
        offset: Int,
        limit: Int,
    ): List<CartItemEntity>

    @Query("DELETE FROM cart_items WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    fun deleteWithProductId(productId: Long)

    @Query("DELETE FROM cart_items")
    fun deleteAll()
}
