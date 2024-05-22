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

    @Query("SELECT * FROM `order` ORDER BY id DESC LIMIT 1")
    fun getLast(): OrderEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tickets: OrderEntity)

    @Delete
    fun delete(tickets: OrderEntity)
}
