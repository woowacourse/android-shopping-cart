package woowacourse.shopping.data.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartProductDao {
    @Query("SELECT * from cart_product")
    fun getAll(): List<CartProductEntity>

    @Query("SELECT COUNT(*) FROM cart_product")
    fun count(): Int

    @Query("SELECT * FROM cart_product LIMIT :limit OFFSET :offset")
    fun getPaged(
        limit: Int,
        offset: Int,
    ): List<CartProductEntity>

    @Query("SELECT quantity FROM cart_product WHERE product_id = :productId")
    fun getQuantityByProductId(productId: Long): Int?

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM cart_product")
    fun getTotalQuantity(): Int

    @Query("UPDATE cart_product SET quantity = :quantity WHERE product_id = :productId")
    fun updateQuantity(
        productId: Long,
        quantity: Int,
    )

    @Insert
    fun insert(product: CartProductEntity)

    @Query("DELETE from cart_product WHERE product_id == :productId")
    fun deleteByProductId(productId: Long)
}
