package woowacourse.shopping.data.product

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun findAll(): List<ProductEntity>

    @Query("SELECT * FROM cart WHERE id =:id")
    fun findById(id: Long): ProductEntity

    @Insert
    fun insert(productEntity: ProductEntity)

    @Insert
    fun insertAll(vararg productEntity: ProductEntity)

    @Delete
    fun delete(productEntity: ProductEntity)
}
