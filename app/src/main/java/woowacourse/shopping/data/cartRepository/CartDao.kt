package woowacourse.shopping.data.cartRepository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAllProducts(): List<CartEntity>

    @Query("SELECT * FROM cart ORDER BY id ASC LIMIT :limit")
    fun getLimitProducts(limit: Int): List<CartEntity>

    @Query("SELECT * FROM cart WHERE id = :id")
    fun getProduct(id: Long): CartEntity

    @Query("SELECT COUNT(*) FROM cart")
    fun getAllProductsSize(): Int

    @Insert
    fun insert(cartEntity: CartEntity)

    @Query("DELETE FROM cart WHERE id = :cartItemId")
    fun deleteById(cartItemId: Long)
}
