package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.model.local.CartProductEntity

@Dao
interface ShoppingCartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartProduct(cartProductEntity: CartProductEntity)

    @Query("UPDATE cartProductEntity SET quantity = :quantity WHERE productId = :productId")
    fun updateCartProduct(
        productId: Long,
        quantity: Int,
    )

    @Query("SELECT * FROM cartProductEntity WHERE productId = :productId")
    fun findCartProduct(productId: Long): CartProductEntity

    @Query("SELECT * FROM cartProductEntity LIMIT :pageSize OFFSET (:page * :pageSize)")
    fun getCartProductsPaged(
        page: Int,
        pageSize: Int,
    ): List<CartProductEntity>

    @Query("SELECT COUNT(*) FROM cartProductEntity")
    fun getCartProductsTotal(): Int

    @Query("DELETE FROM cartProductEntity WHERE productId = :productId")
    fun deleteCartProduct(productId: Long)

    @Query("DELETE FROM cartProductEntity")
    fun deleteAllCartProduct()
}
