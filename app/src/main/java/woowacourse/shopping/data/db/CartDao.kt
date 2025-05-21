package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CartDao {
    @Query("SELECT * FROM CartEntity ORDER BY createdAt ASC Limit :limit OFFSET :offset")
    fun getCartItemPaged(
        limit: Int,
        offset: Int,
    ): List<CartEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM CartEntity WHERE createdAt > :createdAt)")
    fun existsItemCreatedAfter(createdAt: Long): Boolean

    @Query("SELECT * FROM CartEntity WHERE productId = :productId")
    fun findByProductId(productId: Long): CartEntity?

    @Query("SELECT quantity FROM CartEntity WHERE productId = :productId")
    fun findQuantityByProductId(productId: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: CartEntity)

    @Query("UPDATE CartEntity SET quantity = quantity + 1 WHERE productId = :productId")
    fun updateQuantity(productId: Long)

    @Query("UPDATE CartEntity SET quantity = quantity - 1 WHERE productId = :productId AND quantity > 1")
    fun decreaseQuantity(productId: Long)

    @Transaction
    fun insertOrUpdate(product: CartEntity) {
        val existingProduct = findByProductId(product.productId)
        if (existingProduct == null) insert(product) else updateQuantity(product.productId)
    }

    @Transaction
    fun decreaseOrDelete(productId: Long) {
        val foundProduct = findByProductId(productId)
        if (foundProduct != null && foundProduct.quantity <= 1) delete(productId) else decreaseQuantity(productId)
    }

    @Query("DELETE FROM CartEntity WHERE productId = :id")
    fun delete(id: Long)
}
