package woowacourse.shopping.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    suspend fun getAll(): List<CartEntity>

    @Query("SELECT * FROM cart WHERE productId = :id")
    suspend fun findById(id: String): CartEntity?

    @Query("SELECT COUNT(*) FROM cart")
    suspend fun getTotalCartSize(): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertItem(entity: CartEntity)

    @Update
    suspend fun updateItem(entity: CartEntity)

    @Query("DELETE FROM cart WHERE productId = :id")
    suspend fun deleteItem(id: String): Int

    @Transaction
    suspend fun addOrIncrement(id: String) {
        val existing = findById(id)
        if (existing == null) {
            insertItem(CartEntity(id, 1))
        } else {
            updateItem(existing.copy(quantity = existing.quantity + 1))
        }
    }

    @Transaction
    suspend fun deleteOrDecrement(id: String) {
        val existing = findById(id) ?: return
        if (existing.quantity == 1) {
            deleteItem(id)
        } else {
            updateItem(existing.copy(quantity = existing.quantity - 1))
        }
    }
}
