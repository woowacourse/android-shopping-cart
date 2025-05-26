package woowacourse.shopping.data.cart

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAll(): List<CartEntity>

    @Insert
    fun insertAll(vararg cartEntities: CartEntity)

    @Transaction
    fun insertAndUpdateQuantity(cartEntity: CartEntity) {
        insertAll(cartEntity)
        update(cartEntity.copy(quantity = 1))
    }

    @Transaction
    fun updateQuantity(cartEntity: CartEntity) {
        val existing = findById(cartEntity.id)
        if (existing != null) {
            update(cartEntity.copy(quantity = cartEntity.quantity))
        } else {
            insertAll(cartEntity)
        }
    }

    @Delete
    fun delete(cartEntity: CartEntity)

    @Query("SELECT * FROM cart LIMIT :limit OFFSET :offset")
    fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<List<CartEntity>>

    @Query("SELECT COUNT(*) FROM cart")
    fun getAllItemsSize(): Int

    @Query("SELECT SUM(quantity) FROM cart")
    fun getTotalQuantity(): Int

    @Query("SELECT * FROM cart WHERE id = :id LIMIT 1")
    fun findById(id: Long): CartEntity?

    @Update
    fun update(cartEntity: CartEntity)
}
