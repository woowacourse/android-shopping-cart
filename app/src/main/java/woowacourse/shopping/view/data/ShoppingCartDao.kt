package woowacourse.shopping.view.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.product.entity.CartItemEntity

@Dao
interface ShoppingCartDao {
    @Query("SELECT * FROM shoppingCart")
    fun load(): List<CartItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(entity: CartItemEntity)

    @Delete
    fun remove(cartItem: CartItemEntity)

    fun replaceAll(shoppingCart: List<CartItemEntity>) {
        deleteAll()
        insertAll(shoppingCart)
    }

    @Query("DELETE FROM shoppingCart")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<CartItemEntity>)

    @Query("SELECT quantity FROM shoppingCart WHERE id = :productId")
    fun quantityOf(productId: Long): Int
}
