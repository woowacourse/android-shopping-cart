package woowacourse.shopping.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items ORDER BY rowid ASC")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    fun getCartItem(productId: String): Flow<CartEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cartEntity: CartEntity): Long

    @Update
    fun update(cartEntity: CartEntity)

    @Transaction
    fun insertOrUpdate(cartEntity: CartEntity) {
        val id = insert(cartEntity)
        if (id == -1L) {
            update(cartEntity)
        }
    }

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    fun deleteCartItem(productId: String)

    @Query("SELECT COUNT(*) FROM cart_items")
    fun getCartItemCount(): Int

    @Query("SELECT * FROM cart_items ORDER BY rowid ASC LIMIT :limit OFFSET :offset")
    fun getPagingCartItems(limit: Int, offset: Int): List<CartEntity>
}
