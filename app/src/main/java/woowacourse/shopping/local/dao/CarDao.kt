package woowacourse.shopping.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import woowacourse.shopping.local.entity.CartEntity

@Dao
interface CarDao {
    @Insert
    fun saveItemCart(product: CartEntity): Long

    @Query("SELECT * FROM cart")
    fun findAllCartItem(): List<CartEntity>

    @Query("DELETE FROM cart")
    fun clearAllCartItems()

    @Query("DELETE FROM cart WHERE productId =:productId")
    fun clearCartItemById(productId: Long)

    @Query("SELECT * FROM cart WHERE productId = :productId")
    fun findCartItemById(productId: Long): CartEntity?

    @Update
    fun updateCartItem(product: CartEntity): Int
}
