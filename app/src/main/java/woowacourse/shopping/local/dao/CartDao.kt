package woowacourse.shopping.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.local.entity.CartEntity

@Dao
interface CartDao {
    @Query("SELECT * FROM Cart LIMIT :size OFFSET :offset")
    fun loadCart(
        offset: Int,
        size: Int,
    ): List<CartEntity>

    @Query("SELECT * FROM Cart WHERE id IN (:ids)")
    fun filterCartProducts(ids: List<Long>): List<CartEntity>

    @Query("SELECT COUNT(*) > :size FROM Cart")
    fun canLoadMore(size: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCart(cart: CartEntity): Long

    @Query("DELETE FROM Cart WHERE id = :id")
    fun deleteCart(id: Long): Int

    @Query("DELETE FROM Cart")
    fun deleteAllCarts()
}
