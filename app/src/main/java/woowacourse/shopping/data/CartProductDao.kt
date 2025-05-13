package woowacourse.shopping.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartProductDao {
    @Insert
    fun insertProduct(cartProduct: CartProduct)

    @Delete
    fun deleteProduct(cartProduct: CartProduct)

    @Query("SELECT * FROM cartProducts")
    fun getCartProducts(): List<CartProduct>
}
