package woowacourse.shopping.data.goods

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RecentSeenGoodsDao {
    @Query("SELECT goods_id FROM recent_seen_goods ORDER BY id DESC LIMIT :limit")
    fun getRecentGoodsIds(limit: Int = 10): List<String>

    @Query("DELETE FROM recent_seen_goods WHERE goods_id = :goodsId")
    fun deleteByGoodsId(goodsId: String)

    @Insert
    fun insert(recentGoods: RecentSeenGoodsEntity)

    @Query("DELETE FROM recent_seen_goods WHERE id NOT IN (SELECT id FROM recent_seen_goods ORDER BY id DESC LIMIT :maxCount)")
    fun deleteOldestItems(maxCount: Int)

    @Transaction
    fun addRecentGoodsWithLimit(
        goodsId: String,
        maxCount: Int = 10,
    ) {
        deleteByGoodsId(goodsId)
        insert(RecentSeenGoodsEntity(goodsId = goodsId))
        deleteOldestItems(maxCount)
    }
}
