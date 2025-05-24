package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.model.LatestGoods

@Entity(tableName = "latestGoods")
data class LatestGoodsEntity(
    @PrimaryKey val goodsId: Int,
    @ColumnInfo val timestamp: Long = System.currentTimeMillis(),
)

fun LatestGoodsEntity.toLatestGoods() =
    LatestGoods(
        goodsId = goodsId,
    )

fun LatestGoods.toLatestGoodsEntity() =
    LatestGoodsEntity(
        goodsId = goodsId,
    )
