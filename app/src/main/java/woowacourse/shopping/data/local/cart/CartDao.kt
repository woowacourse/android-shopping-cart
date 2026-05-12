package woowacourse.shopping.data.local.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items  LIMIT :pageSize OFFSET :startIndex")
    suspend fun pagination(
        startIndex: Int,
        pageSize: Int,
    ): List<CartItemEntity>

    @Query("SELECT * FROM cart_items")
    suspend fun findAll(): List<CartItemEntity>

    @Query("SELECT * FROM cart_items WHERE productId = :id")
    suspend fun findById(id: String): CartItemEntity?

    @Insert
    suspend fun insert(item: CartItemEntity)

    @Update
    suspend fun update(item: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE productId = :id")
    suspend fun deleteById(id: String)
}
