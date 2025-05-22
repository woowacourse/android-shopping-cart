package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAllProducts(): List<CartEntity>

    @Query("SELECT COUNT(product_id) FROM cart")
    fun getAllProductCount(): Int

    @Query("SELECT * FROM cart WHERE product_id = :productId")
    fun getCartItemById(productId: Long): CartEntity?

    @Query("SELECT quantity FROM cart WHERE product_id = :productId")
    fun getQuantityById(productId: Long): Int

    @Query("SELECT SUM(quantity) FROM cart")
    fun getTotalQuantity(): Int?

    @Query("SELECT * FROM cart LIMIT :pageSize OFFSET :offset")
    fun getPagedProducts(
        pageSize: Int,
        offset: Int,
    ): List<CartEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM cart WHERE product_id = :productId)")
    fun existsByProductId(productId: Long): Boolean

    @Query("UPDATE cart SET quantity = quantity + :quantity WHERE product_id = :productId")
    fun increaseQuantity(
        productId: Long,
        quantity: Int,
    )

    @Query("UPDATE cart SET quantity = quantity - 1 WHERE product_id = :productId")
    fun decreaseQuantity(productId: Long)

    @Insert
    fun insertProduct(cartEntity: CartEntity)

    @Query("DELETE FROM cart WHERE product_id = :productId")
    fun deleteProductById(productId: Long)
}
