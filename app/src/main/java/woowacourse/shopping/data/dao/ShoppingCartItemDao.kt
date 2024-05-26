package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.entity.ShoppingCartItemEntity

@Dao
interface ShoppingCartItemDao {
    @Insert
    fun insert(shoppingCartItem: ShoppingCartItemEntity)

    @Insert
    fun insertAll(shoppingCartItems: List<ShoppingCartItemEntity>)

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

    @Query("DELETE FROM shopping_cart_item WHERE product_id = :productId")
    fun delete(productId: Long)

    @Query("SELECT * FROM shopping_cart_item")
    fun shoppingCartItems(): List<ShoppingCartItemEntity>

    @Query("SELECT * FROM shopping_cart_item LIMIT :pageSize OFFSET :pageIndex")
    fun shoppingCartItems(
        pageSize: Int,
        pageIndex: Int,
    ): List<ShoppingCartItemEntity>

    @Query("SELECT COUNT(*) FROM shopping_cart_item")
    fun itemCount(): Int

    @Query("SELECT *  FROM shopping_cart_item WHERE product_id = :productId")
    fun cartItemById(productId: Long): ShoppingCartItemEntity?

    @Query("UPDATE shopping_cart_item SET totalQuantity = :newQuantity WHERE product_id = :productId")
    fun updateTotalQuantity(
        productId: Long,
        newQuantity: Int,
    )
}
