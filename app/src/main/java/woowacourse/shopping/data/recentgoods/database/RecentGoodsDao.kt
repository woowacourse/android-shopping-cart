package woowacourse.shopping.data.recentgoods.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecentGoodsDao {
    @Query("SELECT * FROM recent_goods ORDER BY time DESC LIMIT :count")
    fun getRecentGoods(count: Int): List<RecentGoodsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoods(goods: RecentGoodsEntity)

    @Query("DELETE FROM recent_goods WHERE id NOT IN (SELECT ID FROM recent_goods ORDER BY time DESC LIMIT :count)")
    fun trimGoods(count: Int)
}