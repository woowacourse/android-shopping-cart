package woowacourse.shopping.presentation

import woowacourse.shopping.data.model.CartEntity

object CartFixture {
    fun getFixture(): List<CartEntity> {
        return listOf(CartEntity(1L, 0L, 1, 1), CartEntity(2L, 1L, 1, 1), CartEntity(3L, 2L, 0, 0))
    }
}
