package woowacourse.shopping.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.local.entity.CartEntity
import java.util.UUID

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    suspend fun getAll(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(cartEntity: CartEntity)

    @Query("DELETE FROM cart_items WHERE productId = :id")
    suspend fun deleteById(id: UUID)

    @Query("SELECT * FROM cart_items WHERE productId = :id")
    suspend fun getCartItemById(id: UUID): CartEntity?

    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun getSize(): Int
}
