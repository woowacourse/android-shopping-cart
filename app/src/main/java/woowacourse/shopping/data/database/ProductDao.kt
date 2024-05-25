package woowacourse.shopping.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product LIMIT :pageSize OFFSET :page * :pageSize")
    fun getProductItems(
        page: Int,
        pageSize: Int,
    ): List<Product>

    @Upsert
    fun addProductItem(product: Product): Long

    @Upsert
    fun addAll(product: List<Product>)

    @Delete
    fun deleteProductItem(product: Product)

    @Query("DELETE FROM product")
    fun deleteAll()

    @Transaction
    @Query("SELECT * FROM product WHERE id=:productId")
    fun getCartableProduct(productId: Long): CartableProduct

    @Transaction
    @Query("SELECT * FROM product LIMIT :pageSize OFFSET :page * :pageSize")
    fun getCartableProducts(
        page: Int,
        pageSize: Int,
    ): List<CartableProduct>
}
