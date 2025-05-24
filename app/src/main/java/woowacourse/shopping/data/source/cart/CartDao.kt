package woowacourse.shopping.data.source.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAllProducts(): List<CartEntity>

    @Query("SELECT * FROM cart ORDER BY id DESC LIMIT :limit OFFSET :offset")
    fun getLimitProducts(
        limit: Int,
        offset: Int,
    ): List<CartEntity>

    @Query("SELECT * FROM cart WHERE id = :id")
    fun getProduct(id: Long): CartEntity

    @Query("SELECT id FROM cart WHERE product_id = :productId")
    fun getProductTableId(productId: Long): Long?

    @Query("SELECT COUNT(*) FROM cart")
    fun getAllProductsSize(): Int

    @Insert
    fun insert(cartEntity: CartEntity)

    @Query("DELETE FROM cart WHERE product_id = :productId")
    fun deleteById(productId: Long)

    @Query("UPDATE cart SET product_count = :count WHERE product_id = :productId")
    fun updateCount(
        productId: Long,
        count: Int,
    )
}
