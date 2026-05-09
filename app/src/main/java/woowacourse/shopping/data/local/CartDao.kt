package woowacourse.shopping.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_table")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart_table ORDER BY product_id DESC LIMIT :limit OFFSET :offset")
    suspend fun getCartItems(
        limit: Int,
        offset: Int,
    ): List<CartEntity>

    @Query("SELECT COUNT(*) FROM cart_table")
    fun getCartItemsCount(): Flow<Int>

    @Query("SELECT * FROM cart_table WHERE product_id = :productId")
    suspend fun getCartItem(productId: String): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartEntity)

    @Update
    suspend fun update(item: CartEntity)

    @Query("DELETE FROM cart_table WHERE product_id = :productId")
    suspend fun deleteItem(productId: String)
}
