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

    @Query("SELECT quantity FROM cart_product WHERE product_id = :productId LIMIT 1")
    fun getQuantityByProductId(productId: Long): Int?

    @Insert
    fun insert(product: CartProductEntity)

    @Query("DELETE from cart_product WHERE id == :cartProductId")
    fun delete(cartProductId: Long)
}
