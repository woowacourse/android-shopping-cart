package woowacourse.shopping.data.goods.repository

import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.Goods.Companion.dummyGoods

class FakeGoodsRepositoryImpl : FakeGoodsRepository {
    override fun getGoodsById(
        id: Long,
        callback: (Goods?) -> Unit,
    ) {
        callback(dummyGoods.find { it.id == id })
    }
}
