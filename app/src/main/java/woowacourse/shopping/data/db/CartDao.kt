package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface CartDao {
    @Query("SELECT EXISTS(SELECT 1 FROM CartEntity WHERE id > :id)")
    fun existsItemAfterId(id: Long): Boolean

    @Query("SELECT * FROM CartEntity ORDER BY id ASC Limit :limit OFFSET :offset")
    fun getCartItemPaged(
        limit: Int,
        offset: Int,
    ): List<CartEntity>

    @Query("SELECT * FROM CartEntity WHERE id = :id LIMIT 1")
    fun getByProductId(id: Long): CartEntity?

    @Query("SELECT * FROM CartEntity")
    fun getCartItems(): List<CartEntity>

    @Query("DELETE FROM CartEntity WHERE id = :id")
    fun delete(id: Long)

    @Insert
    fun insert(product: CartEntity)

    @Update
    fun update(product: CartEntity)

    @Transaction
    fun upsert(product: CartEntity) {
        val existing = getByProductId(product.id)
        if (existing == null) {
            insert(product)
        } else {
            val updatedProduct =
                existing.copy(
                    amount = existing.amount + product.amount,
                )
            update(updatedProduct)
        }
    }

    @Query("UPDATE CartEntity SET amount = amount + 1 WHERE id = :id")
    fun increaseAmount(id: Long)

    @Query("UPDATE CartEntity SET amount = amount - 1 WHERE id = :id")
    fun decreaseAmount(id: Long)

    @Transaction
    fun decreaseAmountOrDelete(id: Long) {
        val item = getByProductId(id) ?: return
        if (item.amount <= 1) {
            delete(id)
        } else {
            decreaseAmount(id)
        }
    }
}
