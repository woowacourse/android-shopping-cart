package woowacourse.shopping.local.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import woowacourse.shopping.data.model.ProductIdsCountData

@Dao
interface ShoppingCartDao {
    @Insert
    fun insert(productIdsCountData: ProductIdsCountData): Long

    @Query("SELECT * from shopping_cart_products WHERE productId = :productId")
    fun findById(productId: Long): ProductIdsCountData?

    @Query("SELECT * from shopping_cart_products LIMIT 5 OFFSET :page")
    fun findPaged(page: Int): List<ProductIdsCountData>

    @Query("SELECT * from shopping_cart_products")
    fun findAll(): List<ProductIdsCountData>

    @Query("SELECT COUNT(*) from shopping_cart_products")
    fun countAll(): Int

    @Query("DELETE FROM shopping_cart_products WHERE productId = :productId")
    fun delete(productId: Long)

    @Update
    fun update(productIdsCountData: ProductIdsCountData)

    @Query("DELETE FROM shopping_cart_products")
    fun clearAll()
}
