package woowacourse.shopping.data.shoppingcart.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface ShoppingCartDao {
    @Query("SELECT * FROM shopping_cart")
    fun getAll(): List<ShoppingCartEntity>

    @Query("SELECT * FROM shopping_cart WHERE id = :id LIMIT 1")
    fun getItemById(id: Long): ShoppingCartEntity?

    @Query("SELECT * FROM shopping_cart LIMIT :limit OFFSET :offset")
    fun getPage(offset: Int, limit: Int): List<ShoppingCartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: ShoppingCartEntity)

    @Update
    fun updateItem(item: ShoppingCartEntity)

    @Delete
    fun delete(item: ShoppingCartEntity)

    @Transaction
    fun upsertItem(item: ShoppingCartEntity) {
        val existing = getItemById(item.id)
        if (existing != null) {
            val updated = existing.copy(quantity = existing.quantity + item.quantity)
            updateItem(updated)
        } else {
            insertItem(item)
        }
    }

    @Transaction
    fun upsertItems(items: List<ShoppingCartEntity>) {
        items.forEach { upsertItem(it) }
    }
}