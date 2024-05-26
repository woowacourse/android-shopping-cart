package woowacourse.shopping.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import woowacourse.shopping.data.db.model.OrderEntity

@Dao
interface OrderDao {
    @Insert(onConflict = REPLACE)
    fun putOrder(orderEntity: OrderEntity)

    @Query("select * from OrderEntity")
    fun getOrders(): List<OrderEntity>

    @Query("select * from OrderEntity where productId == :productId")
    fun getOrderByProductId(productId: Int): List<OrderEntity>

    @Query("select * from OrderEntity where orderId == :orderId")
    fun getOrderById(orderId: Int): OrderEntity

    @Delete
    fun removeOrder(orderEntity: OrderEntity)

    @Query("delete from OrderEntity where orderId = :orderId")
    fun removeOrderById(orderId: Int)

    @Query("delete from OrderEntity")
    fun removeAll()

    @Query("select * from OrderEntity where orderId > :offset * :size limit :size")
    fun findByOffsetAndSize(
        offset: Int,
        size: Int,
    ): List<OrderEntity>
}
