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
        "SELECT product.id AS productId, product.name, product.imgUrl, product.price, cart.quantity " +
                "FROM product LEFT JOIN cart ON product.id = cart.productId " +
                "LIMIT :pageSize OFFSET :offset * :pageSize"
    )
    fun findProductByPaging(offset: Int, pageSize: Int): List<CartProduct>

    @Query(
        "SELECT product.id AS productId, product.name, product.imgUrl, product.price, cart.quantity " +
                "FROM cart LEFT JOIN product ON cart.productId = product.id " +
                "LIMIT :pageSize OFFSET :offset * :pageSize"
    )
    fun findCartByPaging(offset: Int, pageSize: Int): List<CartProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCart(cart: Cart)
}