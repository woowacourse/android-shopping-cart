package woowacourse.shopping.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CartDao {
    @Query("SELECT * FROM cart_table")
    abstract fun getAllCartItems(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart_table ORDER BY product_id DESC LIMIT :limit OFFSET :offset")
    abstract suspend fun getCartItems(
        limit: Int,
        offset: Int,
    ): List<CartEntity>

    @Query("SELECT COUNT(*) FROM cart_table")
    abstract fun getCartItemsCount(): Flow<Int>

    @Query("SELECT * FROM cart_table WHERE product_id = :productId")
    abstract suspend fun getCartItem(productId: String): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: CartEntity)

    @Update
    abstract suspend fun update(item: CartEntity)

    @Query("DELETE FROM cart_table WHERE product_id = :productId")
    abstract suspend fun deleteItem(productId: String)

    @Transaction
    open suspend fun addItem(
        productId: String,
        amount: Int,
    ) {
        val target = getCartItem(productId)
        if (target == null) {
            insert(CartEntity(productId, amount))
            return
        }
        update(target.copy(amount = target.amount + amount))
    }

    @Transaction
    open suspend fun minusItem(
        productId: String,
        amount: Int,
    ) {
        val target = getCartItem(productId) ?: return

        if (target.amount - amount <= 0) {
            deleteItem(productId)
            return
        }

        update(target.copy(amount = target.amount - amount))
    }
}
