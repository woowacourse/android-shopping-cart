package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import woowacourse.shopping.data.entity.CartProductEntity

@Dao
interface CartProductDao {
    @Insert(onConflict = REPLACE)
    fun insertCartProduct(cartProduct: CartProductEntity)

    @Delete
    fun deleteCartProduct(cartProduct: CartProductEntity)

    @Query("SELECT * FROM CartProducts LIMIT :endIndex OFFSET :startIndex")
    fun getCartProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<CartProductEntity>

    @Update
    fun updateProduct(product: CartProductEntity)

    @Query("SELECT COUNT(*) FROM CartProducts")
    fun getAllProductsSize(): Int

    @Query("SELECT SUM(quantity) FROM CartProducts")
    fun getCartItemSize(): Int
}
