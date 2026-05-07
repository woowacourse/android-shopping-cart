package woowacourse.shopping.data.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.localdb.entity.CartItemEntity

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cart_items ORDER BY timestamp")
    suspend fun getAll(): List<CartItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun getTotalCount(): Int

    @Query("SELECT SUM(price * quantity) FROM cart_items")
    suspend fun getTotalPrice(): Int
}