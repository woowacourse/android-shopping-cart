package woowacourse.shopping.data.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_item")
    fun getAll(): List<CartItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_item WHERE id = :productId")
    fun delete(productId: Long)

    @Query("UPDATE cart_item SET quantity = :quantity WHERE id = :productId  ")
    fun update(
        productId: Long,
        quantity: Int,
    )

    @Query("SELECT * FROM cart_item LIMIT :limit OFFSET :offset")
    fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<CartItemEntity>

    @Query("DELETE FROM cart_item")
    fun clear()

    @Query("SELECT quantity FROM cart_item WHERE id = :productId")
    fun findQuantityById(productId: Long): Int

    @Query("SELECT * FROM cart_item WHERE id = :productId")
    fun findItemById(productId: Long): CartItemEntity?
}
