package woowacourse.shopping.data.shoppingcart.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingCartDao {
    @Query("SELECT * FROM shopping_cart")
    fun getAll(): List<ShoppingCartEntity>

    @Query("SELECT * FROM shopping_cart WHERE id = :id LIMIT 1")
    fun getItemById(id: Long): ShoppingCartEntity?

    @Query("SELECT * FROM shopping_cart LIMIT :limit OFFSET :offset")
    fun getPage(offset: Int, limit: Int): List<ShoppingCartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ShoppingCartEntity)

    @Query("UPDATE shopping_cart SET quantity = :quantity WHERE id = :id")
    fun update(id: Long, quantity: Int)

    @Delete
    fun delete(item: ShoppingCartEntity)
}