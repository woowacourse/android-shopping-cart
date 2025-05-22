package woowacourse.shopping.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.shopping.data.entity.LatestGoodsEntity

@Dao
interface LatestGoodsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(goods: LatestGoodsEntity)

    @Query("SELECT * FROM latestGoods ORDER BY timestamp DESC")
    fun getAll(): List<LatestGoodsEntity>
}
