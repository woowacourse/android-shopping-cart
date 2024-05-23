package woowacourse.shopping.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.local.entity.RecentProductEntity

@Dao
interface RecentProductDao {
    @Query("SELECT * FROM RecentProduct ORDER BY createdTime DESC LIMIT :size")
    fun loadProducts(size: Int): List<RecentProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProduct(product: RecentProductEntity): Long
}
