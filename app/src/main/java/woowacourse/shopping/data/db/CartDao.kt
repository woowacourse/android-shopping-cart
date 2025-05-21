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

    @Insert
    fun insertProduct(cartEntity: CartEntity)

    @Query("DELETE FROM cart WHERE product_id = :productId")
    fun deleteByProductId(productId: Long)
}
