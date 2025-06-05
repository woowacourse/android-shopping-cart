package woowacourse.shopping.data.goods

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_seen_goods")
data class RecentSeenGoodsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "goods_id")
    val goodsId: String,
)
