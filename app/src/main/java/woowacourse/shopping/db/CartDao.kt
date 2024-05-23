package woowacourse.shopping.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {
    @Query("SELECT * FROM carts")
    fun getAll(): List<Cart>

    @Insert
    fun insert(cart: Cart): Long

    @Update
    fun update(cart: Cart)

    @Delete
    fun delete(cart: Cart): Long

    @Query("SELECT * FROM carts WHERE :productId")
    fun getCart(productId: Long): Cart

    @Query("DELETE FROM carts")
    fun deleteAll(): Long
}