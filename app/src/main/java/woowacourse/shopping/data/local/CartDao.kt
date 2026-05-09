package woowacourse.shopping.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_table ORDER BY product_id DESC LIMIT :limit OFFSET :offset")
    suspend fun getCartItems(
        limit: Int,
        offset: Int,
    ): List<CartEntity>

    @Query("SELECT COUNT(*) FROM cart_table")
    suspend fun getCartItemsCount(): Int

    @Query("SELECT * FROM cart_table WHERE product_id = :productId")
    suspend fun getCartItem(productId: String): CartEntity?

    @Insert
    suspend fun insert(item: CartEntity)

    @Update
    suspend fun update(item: CartEntity)

    @Query("DELETE FROM cart_table WHERE product_id = :productId")
    suspend fun deleteItem(productId: String)

    @Transaction
    suspend fun editItemAmount(
        productId: String,
        amount: Int,
    ) {
        val item = getCartItem(productId)
        if (item == null) {
            insert(CartEntity(productId, amount))
            return
        }
        update(item.copy(amount = item.amount + amount))
    }
}
