package woowacourse.shopping.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CartDao {
    @Query("SELECT * FROM ShoppingCart ORDER BY productId ASC")
    fun getAll(): List<CartEntity>

    @Query("SELECT SUM(quantity) FROM ShoppingCart")
    fun getTotalQuantity(): Int

    @Query("SELECT * FROM ShoppingCart ORDER BY createdAt ASC Limit :limit OFFSET :offset")
    fun getCartItemPaged(
        limit: Int,
        offset: Int,
    ): List<CartEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM ShoppingCart WHERE createdAt > :createdAt)")
    fun existsItemCreatedAfter(createdAt: Long): Boolean

    @Query("SELECT * FROM ShoppingCart WHERE productId = :productId")
    fun findByProductId(productId: Long): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: CartEntity)

    @Query("UPDATE ShoppingCart SET quantity = quantity + :increaseQuantity WHERE productId = :productId")
    fun updateQuantity(
        productId: Long,
        increaseQuantity: Int,
    )

    @Query("UPDATE ShoppingCart SET quantity = quantity - 1 WHERE productId = :productId AND quantity > 1")
    fun decreaseQuantity(productId: Long)

    @Query("DELETE FROM ShoppingCart WHERE productId = :id")
    fun delete(id: Long)

    @Transaction
    fun insertOrUpdate(
        product: CartEntity,
        increaseQuantity: Int,
    ) {
        val existingProduct = findByProductId(product.productId)
        if (existingProduct == null) insert(product) else updateQuantity(product.productId, increaseQuantity)
    }

    @Transaction
    fun decreaseOrDelete(productId: Long) {
        val foundProduct = findByProductId(productId)
        if (foundProduct != null && foundProduct.quantity <= 1) delete(productId) else decreaseQuantity(productId)
    }
}
