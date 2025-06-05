package woowacourse.shopping.data.goods.repository

import woowacourse.shopping.domain.model.Goods

interface GoodsLocalDataSource {
    fun fetchRecentGoodsIds(onComplete: (List<String>) -> Unit)

    fun loggingRecentGoods(
        goods: Goods,
        onComplete: () -> Unit,
    )
}
