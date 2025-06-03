package woowacourse.shopping.data.goods.repository

import woowacourse.shopping.domain.model.Goods

interface FakeGoodsRepository {
    fun getGoodsById(
        id: Long,
        onSuccess: (Goods?) -> Unit,
    )
}
