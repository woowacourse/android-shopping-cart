package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.shopping.data.dto.CartProductDetail
import woowacourse.shopping.data.entity.CartProductEntity

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartProduct(cartProductEntity: CartProductEntity)

    @Query("DELETE FROM cart_products WHERE productId = :productId")
    fun deleteCartProduct(productId: Int)

    @Transaction
    @Query("SELECT * FROM cart_products ORDER BY productId ASC LIMIT :size OFFSET (:page - 1) * :size")
    fun getCartProductDetails(
        page: Int,
        size: Int,
    ): List<CartProductDetail>

    @Query("SELECT COUNT(*) FROM cart_products")
    fun getCartItemCount(): Int

    @Query("SELECT (COUNT(*) + :size - 1) / :size FROM cart_products")
    fun getMaxPageCount(size: Int): Int

    @Query("SELECT * FROM cart_products WHERE productId = :productId")
    fun getCartProductById(productId: Int): CartProductEntity?

    @Transaction
    @Query("SELECT * FROM cart_products WHERE productId = :productId")
    fun getCartProductDetailById(productId: Int): CartProductDetail?
}
