package woowacourse.shopping.presentation

import woowacourse.shopping.data.model.CartEntity

object CartFixture {
    fun getFixture(): List<CartEntity> {
        return listOf(CartEntity(1, 1, 1), CartEntity(2, 1, 1))
    }
}
