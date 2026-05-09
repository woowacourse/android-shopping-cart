package woowacourse.shopping.repository.dao

import androidx.room.Dao
import androidx.room.Query
import woowacourse.shopping.repository.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("INSERT INTO products (name, price, image_url) VALUES (:name, :price, :imageUrl)")
    suspend fun addProduct(
        name: String,
        price: Int,
        imageUrl: String,
    )

    @Query("SELECT * FROM products LIMIT :size OFFSET :offset")
    suspend fun getProducts(
        offset: Int,
        size: Int,
    ): List<ProductEntity>

    @Query("SELECT COUNT(*) FROM products")
    suspend fun getTotalSize(): Int

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductEntity(id: Int): ProductEntity?
}
