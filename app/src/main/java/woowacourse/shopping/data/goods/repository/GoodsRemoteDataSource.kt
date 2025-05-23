package woowacourse.shopping.data.goods.repository

import woowacourse.shopping.domain.model.Goods

interface GoodsRemoteDataSource {
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
}
