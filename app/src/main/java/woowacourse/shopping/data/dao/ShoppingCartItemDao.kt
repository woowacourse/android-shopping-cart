package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.entity.ShoppingCartItemEntity

@Dao
interface ShoppingCartItemDao {
    @Insert
    fun insert(shoppingCartItem: ShoppingCartItemEntity)

    @Query("UPDATE shopping_cart_item SET totalQuantity = totalQuantity + :quantity WHERE product_id = :productId")
    fun increaseCount(
        productId: Long,
        quantity: Int,
    )

    @Query("UPDATE shopping_cart_item SET totalQuantity = totalQuantity - :quantity WHERE product_id = :productId")
    fun decreaseCount(
        productId: Long,
        quantity: Int,
    )

    @Query("DELETE FROM recent_product WHERE product_id = :productId")
    fun delete(productId: Long)

    @Query("SELECT * FROM shopping_cart_item")
    fun shoppingCartItems(): List<ShoppingCartItemEntity>
}
