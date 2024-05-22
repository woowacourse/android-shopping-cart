package woowacourse.shopping.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.db.entity.CartEntity

@Dao
interface CartDao {

    @Query("SELECT * FROM Cart LIMIT :size OFFSET :offset")
    fun loadCart(offset: Int, size: Int): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCart(cart: CartEntity): Long

    @Query("DELETE FROM Cart WHERE id = :id")
    fun deleteCart(id: Long)

    @Query("DELETE FROM Cart")
    fun deleteAllCarts()
}