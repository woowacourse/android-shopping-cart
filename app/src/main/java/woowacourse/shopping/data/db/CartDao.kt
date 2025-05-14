package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAllProduct(): List<CartEntity>

    @Insert
    fun insertProduct(cartEntity: CartEntity)

    @Delete
    fun deleteProduct(cartEntity: CartEntity)
}
