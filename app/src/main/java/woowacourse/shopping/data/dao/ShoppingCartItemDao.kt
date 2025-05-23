package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import woowacourse.shopping.data.entity.ShoppingCartItemEntity
import woowacourse.shopping.data.entity.ShoppingCartItemWithProduct

@Dao
interface ShoppingCartItemDao {
    @Query("SELECT * FROM shopping_cart_item")
    fun findAll(): List<ShoppingCartItemWithProduct>

    @Query("SELECT * FROM shopping_cart_item LIMIT :limit OFFSET :offset")
    fun findAll(
        offset: Int,
        limit: Int,
    ): List<ShoppingCartItemWithProduct>

    @Query("SELECT COUNT(*) FROM shopping_cart_item")
    fun count(): Int

    @Query("DELETE FROM shopping_cart_item WHERE id = :id")
    fun delete(id: Long)

    @Transaction
    fun saveOrInsert(item: ShoppingCartItemEntity) {
        val existingItem = getShoppingCartItemByProductId(item.productId.toString())
        existingItem?.let {
            val updatedQuantity = it.quantity + item.quantity
            val updatedItem = it.copy(quantity = updatedQuantity)
            update(updatedItem)
        } ?: run {
            insert(item)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: ShoppingCartItemEntity)

    @Upsert
    fun update(item: ShoppingCartItemEntity)

    @Query("SELECT SUM(quantity) FROM shopping_cart_item")
    fun totalQuantity(): Int

    @Query("SELECT * FROM shopping_cart_item WHERE product_id = :productId LIMIT 1")
    fun getShoppingCartItemByProductId(productId: String): ShoppingCartItemEntity?
}
