package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.LatestGoods

interface LatestGoodsRepository {
    fun insertLatestGoods(
        goodsId: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )

    fun getAll(
        onSuccess: (List<LatestGoods>) -> Unit,
        onFailure: (String?) -> Unit,
    )

    fun getLast(
        onSuccess: (LatestGoods?) -> Unit,
        onFailure: (String?) -> Unit,
    )
}
