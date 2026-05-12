package woowacourse.shopping.fake

import woowacourse.shopping.domain.model.Money
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductName

fun fakeProduct(id: Long): Product =
    Product(
        id = id,
        name = ProductName(id.toString()),
        price = Money(1250L),
        imageUrl = "",
    )
