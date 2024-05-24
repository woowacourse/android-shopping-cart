package woowacourse.shopping.data.product.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.product.entity.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE id = :id")
    fun findProductOrNull(id: Long): Product?

    @Query("SELECT * FROM products LIMIT :pageSize OFFSET :page * :pageSize")
    fun findProductRange(
        page: Int,
        pageSize: Int,
    ): List<Product>

    @Query("SELECT COUNT(*) FROM products")
    fun totalProductCount(): Int

    @Insert
    fun insertAllProduct(products: List<Product>)
}
