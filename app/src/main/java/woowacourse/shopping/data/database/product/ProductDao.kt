package woowacourse.shopping.data.database.product

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.data.model.product.Product

@Dao
interface ProductDao {
    @Transaction
    @Query("SELECT * FROM product LIMIT :pageSize OFFSET :page * :pageSize")
    fun getCartableProducts(
        page: Int,
        pageSize: Int,
    ): List<CartableProduct>

    @Transaction
    @Query("SELECT * FROM product WHERE id=:productId")
    fun getCartableProduct(productId: Long): CartableProduct

    @Upsert
    fun addAll(product: List<Product>)
}
