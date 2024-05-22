package woowacourse.shopping.presentation.ui

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

val testProduct0 =
    Product.of(
        name = "채채다",
        price = 1000,
        imageUrl = "https://image1.com",
    )

val testProduct1 =
    Product.of(
        name = "악어다",
        price = 1100,
        imageUrl = "https://image1.com",
    )

val testProduct2 =
    Product.of(
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
        imgUrl = "https://image1.com",
        quantity = 1,
    )

val testCartItem1 =
    CartItem(
        id = 1,
        productId = 1,
        productName = "악어다",
        price = 1100,
        imgUrl = "https://image1.com",
        quantity = 2,
    )

val testCartItem2 =
    CartItem(
        id = 2,
        productId = 2,
        productName = "채드다",
        price = 1200,
        imgUrl = "https://image1.com",
        quantity = 3,
    )

val testCartItem3 =
    CartItem(
        id = 3,
        productId = 2,
        productName = "채드다",
        price = 1200,
        imgUrl = "https://image1.com",
        quantity = 3,
    )
val testCartItem4 =
    CartItem(
        id = 4,
        productId = 2,
        productName = "채드다",
        price = 1200,
        imgUrl = "https://image1.com",
        quantity = 3,
    )
val testCartItem5 =
    CartItem(
        id = 5,
        productId = 2,
        productName = "채드다",
        price = 1200,
        imgUrl = "https://image1.com",
        quantity = 3,
    )
