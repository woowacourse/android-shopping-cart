package woowacourse.shopping.data.shoppingCart.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import woowacourse.shopping.data.shoppingCart.local.entity.ShoppingCartProductEntity

@Dao
interface ShoppingCartDao {
    @Query("SELECT * FROM shoppingCart WHERE product_id = :productId LIMIT 1")
    fun getShoppingCartProduct(productId: Long): ShoppingCartProductEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insert(entity: ShoppingCartProductEntity): Long

    @Update
    fun update(entity: ShoppingCartProductEntity)

    @Transaction
    fun increaseQuantity(newShoppingCartProduct: ShoppingCartProductEntity) {
        val shoppingCartProduct = getShoppingCartProduct(newShoppingCartProduct.product.id)
        if (shoppingCartProduct != null) {
            update(shoppingCartProduct.copy(quantity = shoppingCartProduct.quantity + newShoppingCartProduct.quantity))
        } else {
            insert(newShoppingCartProduct)
        }
    }

    @Query("SELECT * FROM shoppingCart LIMIT :limit OFFSET :offset")
    fun getShoppingCartProducts(
        offset: Int,
        limit: Int,
    ): List<ShoppingCartProductEntity>

    @Transaction
    fun decreaseQuantity(productId: Long) {
        val existing = getShoppingCartProduct(productId) ?: return

        when {
            existing.quantity == 1 -> delete(existing.product.id)
            existing.quantity > 1 -> {
                val updated = existing.copy(quantity = existing.quantity - 1)
                update(updated)
            }
        }
    }

    @Query("SELECT COALESCE(quantity, 0) FROM shoppingCart WHERE product_id = :productId")
    fun getQuantity(productId: Long): Int

    @Query("DELETE FROM shoppingCart WHERE product_id = :productId")
    fun delete(productId: Long)

    @Query("SELECT IFNULL(SUM(quantity), 0) FROM shoppingCart")
    fun getTotalQuantity(): Int
}
