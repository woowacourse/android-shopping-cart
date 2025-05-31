package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Goods

interface GoodsRepository {
    fun getById(
        id: Int,
        onSuccess: (Goods?) -> Unit,
    )

    fun getPagedGoods(
        page: Int,
        count: Int,
        onSuccess: (List<Goods>) -> Unit,
        onFailure: (String?) -> Unit,
    )

    fun getGoodsListByIds(
        ids: List<Int>,
        onSuccess: (List<Goods>) -> Unit,
        onFailure: (String?) -> Unit,
    )
}
