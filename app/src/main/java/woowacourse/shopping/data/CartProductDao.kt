package woowacourse.shopping.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartProductDao {

    @Query("SELECT * FROM cart_products")
    fun getAll(): List<CartProductEntity>

    @Insert
    fun insert(cartProduct: CartProductEntity)

    @Query("DELETE FROM cart_products WHERE id = :id")
    fun delete(id: Long)
}
