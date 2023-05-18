package woowacourse.shopping.presentation

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.model.CartProductEntity
import woowacourse.shopping.presentation.model.CartProductModel

object CartProductFixture {
    fun getFixture(): List<CartProductModel> {
        return listOf(CartProductEntity(1, 1, false), CartProductEntity(2, 3, false)).map {
            it.toUIModel()
        }
    }
}
