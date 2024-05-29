package woowacourse.shopping.helper

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

val testProduct0 =
    Product(
        id = 0L,
        name = "채채다",
        price = 1000,
        imageUrl = "https://image1.com",
    )

val testProduct1 =
    Product(
        id = 1L,
        name = "악어다",
        price = 1100,
        imageUrl = "https://image1.com",
    )

val testProduct2 =
    Product(
        id = 2L,
        name = "채드다",
        price = 1200,
        imageUrl = "https://image1.com",
    )

val testProduct3 =
    Product(
        id = 3L,
        name = "채채다",
        price = 1000,
        imageUrl = "https://image1.com",
    )

val testProduct4 =
    Product(
        id = 4L,
        name = "악어다",
        price = 1100,
        imageUrl = "https://image1.com",
    )

val testProduct5 =
    Product(
        id = 5L,
        name = "채드다",
        price = 1200,
        imageUrl = "https://image1.com",
    )

val testCartItem0 =
    CartItem(
        id = 0,
        productId = 0,
        productName = "채채다",
        price = 1000,
        imageUrl = "https://image1.com",
        quantity = 1,
    )

val testCartItem1 =
    CartItem(
        id = 1,
        productId = 1,
        productName = "악어다",
        price = 1100,
        imageUrl = "https://image1.com",
        quantity = 2,
    )

val testCartItem2 =
    CartItem(
        id = 2,
        productId = 2,
        productName = "채드다",
        price = 1200,
        imageUrl = "https://image1.com",
        quantity = 3,
    )
