package woowacourse.shopping.data.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CartDao {
    @Transaction
    @Query("SELECT * FROM cart LIMIT :limit OFFSET :offset")
    fun findCartItemsInRange(
        limit: Int,
        offset: Int,
    ): List<CartItemDetail>

    @Transaction
    @Query("SELECT * FROM cart WHERE id = :cartItemId")
    fun findByCartItemId(cartItemId: Long): CartItemDetail

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cartItemEntity: CartItemEntity): Long

    @Transaction
    @Query("DELETE FROM cart WHERE id = :cartItemId")
    fun delete(cartItemId: Long)

    @Transaction
    @Query("UPDATE cart SET quantity = quantity + :additional WHERE product_id = :productId")
    fun addQuantity(productId: Long, additional: Int)
}
