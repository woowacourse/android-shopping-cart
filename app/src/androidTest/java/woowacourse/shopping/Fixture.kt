package woowacourse.shopping

import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product

object Fixture {
    val dummyCartEntity =
        CartEntity(
            0,
            0,
            10,
        )

    val dummyProduct =
        Product(
            0,
            "맥심 모카골드 마일드",
            Price(12000),
            "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg",
        )
}
