package woowacourse.shopping.data.recentgoods.mapper

import woowacourse.shopping.data.recentgoods.database.RecentGoodsEntity
import woowacourse.shopping.domain.model.Goods

fun Goods.toEntity(time: Long): RecentGoodsEntity {
    return RecentGoodsEntity(
        id = this.id,
        time = time,
    )
}
