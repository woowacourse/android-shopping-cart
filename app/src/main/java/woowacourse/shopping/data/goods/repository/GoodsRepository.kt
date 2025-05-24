package woowacourse.shopping.data.goods.repository

import woowacourse.shopping.domain.model.Goods

interface GoodsRepository {
    fun fetchGoodsSize(onComplete: (Int) -> Unit)

    fun fetchPageGoods(
        limit: Int,
        offset: Int,
        onComplete: (List<Goods>) -> Unit,
    )

    fun fetchGoodsById(
        id: Int,
        onComplete: (Goods?) -> Unit,
    )

    fun fetchRecentGoodsIds(onComplete: (List<String>) -> Unit)

    fun fetchRecentGoods(onComplete: (List<Goods>) -> Unit)

    fun fetchMostRecentGoods(onComplete: (Goods?) -> Unit)

    fun loggingRecentGoods(
        goods: Goods,
        onComplete: () -> Unit,
    )
}
