package woowacourse.shopping.data.cart.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.cart.entity.CartItem
import woowacourse.shopping.model.Quantity

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cartItem: CartItem): Long

    @Query("UPDATE cart SET quantity = :quantity WHERE product_id = :productId")
    fun changeQuantity(
        productId: Long,
        quantity: Quantity,
    )

    @Query("DELETE FROM cart WHERE product_id = :productId")
    fun delete(productId: Long)

    fun find(productId: Long): CartItem {
        return findOrNull(productId) ?: throw IllegalArgumentException()
    }

    @Query("SELECT * FROM cart WHERE product_id = :productId")
    fun findOrNull(productId: Long): CartItem?

    @Query("SELECT * FROM cart LIMIT :pageSize OFFSET :page * :pageSize")
    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    @Query("SELECT COUNT(*) FROM cart")
    fun totalCount(): Int
}
