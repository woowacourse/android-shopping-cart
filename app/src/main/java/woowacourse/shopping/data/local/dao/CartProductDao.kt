package woowacourse.shopping.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.local.entity.Cart
import woowacourse.shopping.data.local.entity.CartProduct
import woowacourse.shopping.data.local.entity.Product


@Dao
interface CartProductDao {
    @Query(
        "SELECT product.id AS productId, name, imgUrl, price, cart.quantity FROM product, cart " +
                "LIMIT :pageSize OFFSET :offset * :pageSize"
    )
    fun findByPaging(offset: Int, pageSize: Int): List<CartProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCart(cart: Cart)
}