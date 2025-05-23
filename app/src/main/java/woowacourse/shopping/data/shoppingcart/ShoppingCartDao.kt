package woowacourse.shopping.data.shoppingcart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoppingCartDao {
    @Query("SELECT * from shopping_cart")
    fun getAll(): List<ShoppingCartEntity>

    @Query("SELECT COUNT(*) FROM shopping_cart")
    fun count(): Int

    @Query("SELECT * FROM shopping_cart LIMIT :limit OFFSET :offset")
    fun getPaged(
        limit: Int,
        offset: Int,
    ): List<ShoppingCartEntity>

    @Insert
    fun insert(product: ShoppingCartEntity)

    @Query("SELECT * FROM shopping_cart WHERE product_id = :productId LIMIT 1")
    fun getByProductId(productId: Long): ShoppingCartEntity?

    @Query("UPDATE shopping_cart SET quantity = quantity + 1 WHERE product_id = :productId")
    fun increaseQuantity(productId: Long)

    @Query("UPDATE shopping_cart SET quantity = quantity - 1 WHERE product_id = :productId")
    fun decreaseQuantity(productId: Long)

    @Query("DELETE from shopping_cart WHERE product_id == :productId")
    fun delete(productId: Long)

    @Query("SELECT quantity FROM shopping_cart WHERE product_id = :productId")
    fun getQuantity(productId: Long): Int
}
