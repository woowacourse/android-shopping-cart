package woowacourse.shopping.data.db.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {
    @Query("SELECT * FROM carts")
    fun getAll(): List<Cart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: Cart): Long

    @Update
    fun update(cart: Cart)

    @Delete
    fun delete(cart: Cart)

    @Query("SELECT * FROM carts WHERE productId = :productId")
    fun getCart(productId: Long): Cart?

    @Query("DELETE FROM carts")
    fun deleteAll()
}
