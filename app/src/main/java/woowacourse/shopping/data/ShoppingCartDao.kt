package woowacourse.shopping.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoppingCartDao {
    @Query("SELECT * from productEntity")
    fun getAll(): List<ProductEntity>

    @Insert
    fun insertAll(vararg product: ProductEntity)

    @Query("DELETE from productEntity WHERE product_id == :productId")
    fun delete(productId: Long)
}
