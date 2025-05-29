package woowacourse.shopping.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CartDao {
    @Query("SELECT * FROM shopping_cart")
    fun getAllCartItems(): List<ShoppingCartEntity>

    @Upsert
    fun upsertCartItem(cartItem: ShoppingCartEntity)

    @Query("DELETE FROM shopping_cart WHERE productId = :productId")
    fun deleteCartItem(productId: Long)
}
