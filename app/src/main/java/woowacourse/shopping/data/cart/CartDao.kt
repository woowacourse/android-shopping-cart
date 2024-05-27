package woowacourse.shopping.data.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.shopping.model.Quantity

@Dao
interface CartDao {
    @Query("SELECT COUNT(*) FROM carts")
    fun itemSize(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: Cart): Long

    @Query("SELECT * FROM carts WHERE id = :id")
    fun find(id: Long): Cart

    @Query("SELECT * FROM carts")
    fun findAll(): List<Cart>

    @Query("SELECT * FROM carts LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Cart>

    @Query("DELETE FROM carts WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM carts WHERE productId = :productId")
    fun deleteByProductId(productId: Long)

    @Transaction
    fun plusQuantityByProductId(productId: Long) {
        val updatedRows = updateQuantityIfProductExists(productId)
        if (updatedRows == 0) {
            insert(Cart(productId = productId, quantity = Quantity(1)))
        }
    }

    @Query("UPDATE carts SET quantity = quantity + 1 WHERE productId = :productId")
    fun updateQuantityIfProductExists(productId: Long): Int

    @Transaction
    fun minusQuantityByProductId(productId: Long) {
        val cart = findByProductId(productId)
        if (cart != null) {
            if (cart.quantity.value > 1) {
                updateQuantityIfGreaterThanOne(productId)
            } else {
                deleteByProductId(productId)
            }
        }
    }

    @Query("SELECT * FROM carts WHERE productId = :productId LIMIT 1")
    fun findByProductId(productId: Long): Cart?

    @Query("UPDATE carts SET quantity = quantity - 1 WHERE productId = :productId AND quantity > 1")
    fun updateQuantityIfGreaterThanOne(productId: Long)

    @Query("DELETE FROM carts")
    fun deleteAll()
}
