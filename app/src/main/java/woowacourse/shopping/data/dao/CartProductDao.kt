package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.entity.CartProductEntity

@Dao
interface CartProductDao {
    @Insert
    fun insert(cartProductEntity: CartProductEntity)

    @Query("SELECT * FROM cart_product LIMIT :limit OFFSET :offset")
    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): List<CartProductEntity>

    @Query("SELECT COUNT(*) FROM cart_product")
    fun getTotalCount(): Int

    @Query("SELECT quantity FROM cart_product WHERE product_id = :productId")
    fun getQuantityByProductId(productId: Long): Int?

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM cart_product")
    fun getTotalQuantity(): Int

    @Query("UPDATE cart_product SET quantity = :quantity WHERE product_id = :productId")
    fun updateQuantity(
        productId: Long,
        quantity: Int,
    )

    @Query("DELETE from cart_product WHERE product_id == :productId")
    fun deleteByProductId(productId: Long)
}
