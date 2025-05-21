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

    @Query("SELECT SUM(quantity) FROM cart")
    fun getTotalQuantity(): Int?

    @Query("SELECT product_id FROM cart LIMIT :pageSize OFFSET :offset")
    fun getPagedProductIds(
        pageSize: Int,
        offset: Int,
    ): List<Long>

    @Query("UPDATE cart SET quantity = quantity + 1 WHERE product_id = :productId")
    fun increaseQuantity(productId: Long)

    @Query("UPDATE cart SET quantity = quantity - 1 WHERE product_id = :productId")
    fun decreaseQuantity(productId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM cart WHERE product_id = :productId)")
    fun existsByProductId(productId: Long): Boolean

    @Insert
    fun insertProduct(cartEntity: CartEntity)

    @Query("DELETE FROM cart WHERE product_id = :productId")
    fun deleteByProductId(productId: Long)
}
