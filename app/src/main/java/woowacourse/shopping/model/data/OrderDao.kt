package woowacourse.shopping.model.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderDao {
    @Query("SELECT * FROM `order`")
    fun getAll(): List<OrderEntity>

    @Query("DELETE FROM `order`")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg orders: OrderEntity)

    @Delete
    fun delete(order: OrderEntity)

    @Query("DELETE FROM `order` WHERE productId = :id")
    fun deleteById(id: Long)
}
