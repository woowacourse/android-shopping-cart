package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.shopping.data.entity.RecentProductEntity
import java.time.LocalDateTime

@Dao
interface RecentProductDao {
    @Insert
    fun insert(recentProduct: RecentProductEntity)

    @Query("SELECT * FROM recent_product ORDER BY look_date_time DESC LIMIT 1")
    fun lastRecentProduct(): RecentProductEntity?

    @Query("SELECT * FROM recent_product WHERE product_id = :productId")
    fun recentByProductId(productId: Long): RecentProductEntity

    @Query("SELECT * FROM recent_product")
    fun recentProducts(): List<RecentProductEntity>

    @Query("UPDATE recent_product SET look_date_time = :localDateTime WHERE product_id = :productId")
    fun updateLocalDateTime(
        productId: Long,
        localDateTime: LocalDateTime,
    )
}
