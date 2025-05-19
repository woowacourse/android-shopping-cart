package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Query
import woowacourse.shopping.data.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE id > :lastId ORDER BY id ASC LIMIT :count")
    fun getNextProducts(
        lastId: Int,
        count: Int,
    ): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProduct(id: Int): ProductEntity?

    @Query("SELECT MAX(id) FROM products")
    fun getMaxId(): Int
}
