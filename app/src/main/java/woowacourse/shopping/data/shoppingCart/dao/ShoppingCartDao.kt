package woowacourse.shopping.data.shoppingCart.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.shoppingCart.entity.ShoppingCartProductEntity

@Dao
interface ShoppingCartDao {
    @Insert
    fun insertShoppingCart(shoppingCartEntity: ShoppingCartProductEntity)

    @Query("SELECT * FROM shoppingCart LIMIT :limit OFFSET :offset")
    fun getShoppingCartProducts(
        offset: Int,
        limit: Int,
    ): List<ShoppingCartProductEntity>
}
