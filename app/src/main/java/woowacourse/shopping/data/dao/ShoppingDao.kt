package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.db.CartItemWithProduct
import woowacourse.shopping.data.db.RecentProductWithProduct
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.entity.CatalogEntity
import woowacourse.shopping.data.entity.RecentProductEntity
import java.util.UUID

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(products: List<CatalogEntity>)

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<CatalogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartEntity)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteCartItem(productId: UUID)

    @Transaction
    @Query("SELECT * FROM cart_items")
    fun getCartItemsWithProduct(): Flow<List<CartItemWithProduct>>

    @Query("UPDATE cart_items SET amount = :amount WHERE productId = :productId")
    suspend fun updateCartQuantity(productId: UUID, amount: Int)

    @Query("SELECT amount FROM cart_items WHERE productId = :productId")
    suspend fun getCartItemAmount(productId: UUID): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentProduct(recent: RecentProductEntity)

    @Transaction
    @Query("SELECT * FROM recent_products ORDER BY viewedAt DESC LIMIT 10")
    fun getRecentProductsWithDetail(): Flow<List<RecentProductWithProduct>>

}
