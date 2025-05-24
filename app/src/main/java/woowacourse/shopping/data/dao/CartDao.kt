package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import woowacourse.shopping.data.entity.CartEntity

@Dao
interface CartDao {
    @Insert
    fun insert(item: CartEntity)

    @Query("SELECT * FROM cart where productId = :id")
    fun getById(id: Long): CartEntity?

    @Query("SELECT * FROM cart")
    fun getAll(): List<CartEntity>

    @Query("SELECT COUNT(*) FROM cart")
    fun totalSize(): Int

    @Query("DELETE FROM cart WHERE productId = :id")
    fun deleteById(id: Long)

    @Update
    fun update(item: CartEntity)

    @Query("SELECT * FROM cart ORDER BY productId ASC LIMIT :limit OFFSET :offset")
    fun getPaged(
        offset: Int,
        limit: Int,
    ): List<CartEntity>
}
