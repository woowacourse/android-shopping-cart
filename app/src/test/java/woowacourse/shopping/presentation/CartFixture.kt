package woowacourse.shopping.presentation

import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.data.model.CartEntity
import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.presentation.model.CartModel

object CartFixture {
    fun getFixture(): List<CartModel> {
        return listOf(
            CartEntity(
                1,
                ProductEntity(
                    1,
                    "상품1",
                    1000,
                    ""
                )
            ),
            CartEntity(
                2,
                ProductEntity(
                    3,
                    "상품2",
                    2000,
                    ""
                )
            )
        ).map { it.toUIModel() }
    }
}
