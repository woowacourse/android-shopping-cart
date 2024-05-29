package woowacourse.shopping.database.shoppingcart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoppingCartDao {
    @Query("SELECT * FROM shopping_cart")
    fun getAllItems(): List<ShoppingCartItemEntity>?

    @Query("SELECT * FROM shopping_cart WHERE productId = :id")
    fun findItemById(id: Long): ShoppingCartItemEntity?

    @Query("SELECT * FROM shopping_cart LIMIT :pageSize OFFSET :offset")
    fun getItemsInRange(
        offset: Int,
        pageSize: Int,
    ): List<ShoppingCartItemEntity>?

    @Insert
    fun addItem(item: ShoppingCartItemEntity)

    @Query("UPDATE shopping_cart SET quantity = :quantity WHERE productId = :id")
    fun updateItemQuantity(
        id: Long,
        quantity: Int,
    )

    @Query("SELECT SUM(quantity) FROM shopping_cart")
    fun getTotalItemQuantity(): Int

    @Query("DELETE FROM shopping_cart WHERE productId = :id")
    fun deleteItemById(id: Long)
}
