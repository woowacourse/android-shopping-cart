package woowacourse.shopping.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import woowacourse.shopping.data.db.entity.CartEntity

@Dao
interface CartDao {
    @Upsert
    fun upsert(entity: CartEntity)

    @Transaction
    fun upsertWithAccumulation(entity: CartEntity) {
        getCartByProductId(entity.productId)?.let {
            val updated = it.copy(quantity = it.quantity + entity.quantity)
            update(updated)
        } ?: run {
            insert(entity)
        }
    }

    @Query("SELECT * FROM cart_table")
    fun getAll(): List<CartEntity>

    @Query("SELECT * FROM cart_table WHERE productId = :productId")
    fun getCartByProductId(productId: Long): CartEntity?

    @Insert
    fun insert(entity: CartEntity)

    @Update
    fun update(entity: CartEntity)

    @Query("DELETE FROM cart_table WHERE productId = :productId")
    fun deleteByProductId(productId: Long)
}
