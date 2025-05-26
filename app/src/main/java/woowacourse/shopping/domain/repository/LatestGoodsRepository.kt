package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.LatestGoods

interface LatestGoodsRepository {
    fun insertLatestGoods(
        goodsId: Int,
        onSuccess: () -> Unit,
    )

    fun getAll(onSuccess: (List<LatestGoods>) -> Unit)

    fun getLast(onSuccess: (LatestGoods?) -> Unit)
}
