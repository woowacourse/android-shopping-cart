package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CartDao {
    @Query("SELECT EXISTS(SELECT 1 FROM CartEntity WHERE createdAt < :createdAt)")
    fun existsItemCreatedBefore(createdAt: Long): Boolean

    @Query("SELECT * FROM CartEntity ORDER BY createdAt DESC Limit :limit OFFSET :offset")
    fun getCartItemPaged(
        limit: Int,
        offset: Int,
    ): List<CartEntity>

    @Query("DELETE FROM CartEntity WHERE cartId = :id")
    fun delete(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: CartEntity)

    @Query("SELECT * FROM CartEntity WHERE productId = :productId")
    fun findByProductId(productId: Long): CartEntity?

    @Query("UPDATE CartEntity SET quantity = quantity + 1 WHERE productId = :productId")
    fun updateQuantity(productId: Long)

    @Transaction
    fun insertOrUpdate(product: CartEntity) {
        val existingProduct = findByProductId(product.productId)
        if (existingProduct == null) insert(product) else updateQuantity(product.productId)
    }
}
