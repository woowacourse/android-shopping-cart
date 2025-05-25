package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.entity.CartEntity

@Dao
interface CartDao {
    @Query("SELECT * FROM cart LIMIT :limit OFFSET :offset")
     fun fetchPagedCart(limit: Int, offset: Int): List<CartEntity>

    @Query("SELECT COUNT(*) FROM cart")
     fun getTotalCount(): Int

    @Query("SELECT * FROM cart")
     fun getAll(): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun upsert(entity: CartEntity)

    @Query("DELETE FROM cart WHERE productId = :id")
     fun deleteById(id: Int)

    @Query("DELETE FROM cart")
     fun clear()
}
