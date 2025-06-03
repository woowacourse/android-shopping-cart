package woowacourse.shopping.data.goods.repository

import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.Goods.Companion.dummyGoods

class FakeGoodsRepositoryImpl : FakeGoodsRepository {
    override fun getGoodsById(
        id: Long,
        onSuccess: (Goods?) -> Unit,
    ) {
        onSuccess(dummyGoods.find { it.id == id })
    }
}
