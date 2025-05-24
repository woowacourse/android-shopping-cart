package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import woowacourse.shopping.data.entity.RecentlyViewedProductEntity

@Dao
interface RecentlyViewedProductDao {
    @Insert(onConflict = REPLACE)
    fun insertRecentlyViewedProductUid(recentlyViewedProductEntity: RecentlyViewedProductEntity)

    @Query("SELECT * FROM RecentlyViewedProducts")
    fun getRecentlyViewedProductUids(): List<Int>
}
