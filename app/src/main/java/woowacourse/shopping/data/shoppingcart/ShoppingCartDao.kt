package woowacourse.shopping.data.shoppingcart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoppingCartDao {
    @Query("SELECT * from shopping_cart")
    fun getAll(): List<ShoppingCartEntity>

    @Query("SELECT COUNT(*) FROM shopping_cart")
    fun count(): Int

    @Query("SELECT * FROM shopping_cart LIMIT :limit OFFSET :offset")
    fun getPaged(
        limit: Int,
        offset: Int,
    ): List<ShoppingCartEntity>

    @Insert
    fun insert(product: ShoppingCartEntity)

    @Query("DELETE from shopping_cart WHERE id == :shoppingCartId")
    fun delete(shoppingCartId: Long)
}
