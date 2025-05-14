package woowacourse.shopping.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoppingCartDao {
    @Query("SELECT * from productEntity")
    fun getAll(): List<ProductEntity>

    @Insert
    fun insertAll(vararg product: ProductEntity)

    @Delete
    fun delete(product: ProductEntity)
}
