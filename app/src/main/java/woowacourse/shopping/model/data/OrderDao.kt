package woowacourse.shopping.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderDao {
    @Query("SELECT * FROM `orders`")
    fun getAll(): List<OrderEntity>

    @Query("SELECT * FROM `orders` WHERE productId = :id")
    fun getById(id: Long): OrderEntity?

    @Query("DELETE FROM `orders`")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(order: OrderEntity)

    @Query("DELETE FROM `orders` WHERE productId = :id")
    fun deleteById(id: Long)
}
