package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import woowacourse.shopping.data.entity.CartProductEntity

@Dao
interface CartProductDao {
    @Insert(onConflict = REPLACE)
    fun insertCartProduct(cartProduct: CartProductEntity)

    @Delete
    fun deleteCartProduct(cartProduct: CartProductEntity)

    @Query("SELECT * FROM CartProducts WHERE uid = :id")
    fun getCartProduct(id: Int): CartProductEntity?

    @Query("SELECT * FROM CartProducts LIMIT :endIndex OFFSET :startIndex")
    fun getCartProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<CartProductEntity>

    @Query("SELECT * FROM CartProducts WHERE uid IN (:uids)")
    fun getCartProductsByUids(uids: List<Int>): List<CartProductEntity>

    @Query("UPDATE CartProducts SET quantity = quantity + :diff WHERE uid = :id")
    fun updateProduct(
        id: Int,
        diff: Int,
    )

    @Query("SELECT quantity FROM CartProducts WHERE uid = :uid")
    fun getProductQuantity(uid: Int): Int?

    @Query("SELECT COUNT(*) FROM CartProducts")
    fun getAllProductsSize(): Int

    @Query("SELECT SUM(quantity) FROM CartProducts")
    fun getCartItemSize(): Int
}
