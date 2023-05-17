package woowacourse.shopping.presentation

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.model.CartEntity
import woowacourse.shopping.presentation.model.CartModel

object CartFixture {
    fun getFixture(): List<CartModel> {
        return listOf(CartEntity(1, 1), CartEntity(2, 3)).map { it.toUIModel() }
    }
}
