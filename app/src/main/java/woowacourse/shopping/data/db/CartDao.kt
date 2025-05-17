package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAllProduct(): List<CartEntity>

    @Query("SELECT COUNT(*) FROM cart")
    fun getCartProductCount(): Int

    @Query("SELECT * FROM cart LIMIT :pageSize OFFSET :offset ")
    fun getPagedProduct(
        pageSize: Int,
        offset: Int,
    ): List<CartEntity>

    @Insert
    fun insertProduct(cartEntity: CartEntity)

    @Query("DELETE FROM cart WHERE product_id = :productId")
    fun deleteByProductId(productId: Long)
}
