package woowacourse.shopping.data.carts

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAll(): List<CartEntity>

    @Insert
    fun insertAll(vararg cartEntities: CartEntity)

    @Delete
    fun delete(cartEntity: CartEntity)

    @Query("SELECT * FROM cart LIMIT :limit OFFSET :offset")
    fun getPage(
        limit: Int,
        offset: Int,
    ): List<CartEntity>

    @Query("SELECT COUNT(*) FROM cart")
    fun getAllItemsSize(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cartEntity: CartEntity): Long

    @Update
    fun update(cartEntity: CartEntity)

    @Query("SELECT * FROM cart WHERE id = :id")
    fun findByGoodsId(id: Long): CartEntity?

    @Transaction
    fun addOrIncreaseQuantity(cartEntity: CartEntity) {
        val existingItem = findByGoodsId(cartEntity.id)
        if (existingItem == null) {
            insert(cartEntity)
        } else {
            val quantityUpdatedItem =
                existingItem.copy(
                    quantity = existingItem.quantity + cartEntity.quantity,
                )
            update(quantityUpdatedItem)
        }
    }

    @Transaction
    fun removeOrDecreaseQuantity(cartEntity: CartEntity) {
        val existingItem = findByGoodsId(cartEntity.id)
        if (existingItem != null) {
            val quantityUpdatedItem =
                existingItem.copy(
                    quantity = existingItem.quantity - cartEntity.quantity,
                )
            if (quantityUpdatedItem.quantity > 0) {
                update(quantityUpdatedItem)
            } else {
                delete(existingItem)
            }
        }
    }
}
