package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.entity.CartEntity
import java.util.UUID

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    suspend fun getAll(): List<CartEntity>

    @Upsert
    suspend fun upsert(cartEntity: CartEntity)

    @Query("DELETE FROM cart_items WHERE productId = :id")
    suspend fun delete(id: UUID)

    @Query("SELECT * FROM cart_items WHERE productId = :id")
    suspend fun getCartItemById(id: UUID): CartEntity?

    @Query("SELECT * FROM cart_items ORDER BY productId ASC LIMIT :count OFFSET :fromIndex")
    suspend fun getPagedEntities(fromIndex: Int, count: Int): List<CartEntity>

    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun getSize(): Int

    @Query("SELECT quantity FROM cart_items WHERE productId = :id")
    suspend fun getQuantity(id: UUID): Int?

    @Query("SELECT * FROM cart_items")
    fun observeAll(): Flow<List<CartEntity>>
}
