package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM product WHERE id > :lastId ORDER BY id ASC LIMIT :count")
    fun fetchProducts(count: Int, lastId: Int): List<ProductEntity>

    @Query("SELECT * FROM product")
     fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM product WHERE id = :id")
     fun getById(id: Int): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(products: List<ProductEntity>)

    @Delete
     fun delete(product: ProductEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM product WHERE id > :lastId)")
    fun hasMoreThan(lastId: Int): Boolean
}