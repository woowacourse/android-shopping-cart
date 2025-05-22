package woowacourse.shopping

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Product

object Fixture {
    val dummyProduct =
        Product(
            0,
            "맥심 모카골드 마일드",
            Price(12000),
            "https://sitem.ssgcdn.com/64/93/82/item/0000006829364_i1_464.jpg",
        )

    val mockedCartItems =
        List(10) {
            CartItem(
                product =
                    Product(
                        productId = it.toLong(),
                        imageUrl = "",
                        name = "Product $it",
                        _price = Price(1000),
                    ),
                quantity = 1,
            )
        }
}
