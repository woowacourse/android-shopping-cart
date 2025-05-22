package woowacourse.shopping.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import woowacourse.shopping.data.db.entity.CartEntity

@Dao
interface CartDao {
    @Upsert
    fun upsert(entity: CartEntity)

    @Query("SELECT * FROM cart_table WHERE productId = :productId")
    fun cartByProductId(productId: Long): CartEntity?

    @Query("SELECT * FROM cart_table ORDER BY productId ASC LIMIT :size OFFSET (:page - 1) * :size")
    fun cartSinglePage(
        page: Int,
        size: Int,
    ): List<CartEntity>

    @Query("SELECT (COUNT(*) + :size - 1) / :size FROM cart_table")
    fun pageCount(size: Int): Int

    @Insert
    fun insert(entity: CartEntity)

    @Update
    fun update(entity: CartEntity)

    @Query("DELETE FROM cart_table WHERE productId = :productId")
    fun deleteByProductId(productId: Long)
}
