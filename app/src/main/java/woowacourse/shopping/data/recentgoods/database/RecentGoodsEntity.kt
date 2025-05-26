package woowacourse.shopping.data.recentgoods.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("recent_goods")
data class RecentGoodsEntity(
    @PrimaryKey val id: Long,
    val time: Long,
)