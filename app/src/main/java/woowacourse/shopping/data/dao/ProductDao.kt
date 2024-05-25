package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun products(): List<ProductEntity>

    @Insert
    fun insertAll(products: List<ProductEntity>)

    @Insert
    fun insert(product: ProductEntity)

    @Query("SELECT * FROM product WHERE id = :productId")
    fun productById(productId: Long): ProductEntity

    @Query("SELECT * FROM product LIMIT :pageSize OFFSET :pageIndex")
    fun shoppingCartItems(
        pageSize: Int,
        pageIndex: Int,
    ): List<ProductEntity>
}
