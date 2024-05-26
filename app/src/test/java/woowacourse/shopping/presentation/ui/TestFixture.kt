import woowacourse.shopping.data.mapper.toProductEntity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.model.RecentlyViewedProduct

val testProduct0 =
    Product(
        id = 0,
        name = "채채다",
        price = 1000,
        imageUrl = "https://image1.com",
    )

val testProduct1 =
    Product(
        id = 1,
        name = "악어다",
        price = 1100,
        imageUrl = "https://image1.com",
    )

val testProduct2 =
    Product(
        id = 2,
        name = "채드다",
        price = 1200,
        imageUrl = "https://image1.com",
    )

val testProduct3 =
    Product(
        id = 3,
        name = "채채다",
        price = 1000,
        imageUrl = "https://image1.com",
    )

val testProduct4 =
    Product(
        id = 4,
        name = "악어다",
        price = 1100,
        imageUrl = "https://image1.com",
    )

val testProduct5 =
    Product(
        id = 5,
        name = "채드다",
        price = 1200,
        imageUrl = "https://image1.com",
    )

// ProductWithQuantity
val testProductWithQuantity0 = ProductWithQuantity(testProduct0.toProductEntity(), 1)
val testProductWithQuantity1 = ProductWithQuantity(testProduct1.toProductEntity(), 2)
val testProductWithQuantity2 = ProductWithQuantity(testProduct2.toProductEntity(), 3)
val testProductWithQuantity3 = ProductWithQuantity(testProduct3.toProductEntity(), 4)
val testProductWithQuantity4 = ProductWithQuantity(testProduct4.toProductEntity(), 5)
val testProductWithQuantity5 = ProductWithQuantity(testProduct5.toProductEntity(), 6)

// RecentlyViewedProduct
val testRecentlyViewedProduct0 =
    RecentlyViewedProduct(
        productId = 0,
        name = "채채다",
        price = 1000,
        imageUrl = "https://image1.com",
        viewedAt = 0,
    )

val testRecentlyViewedProduct1 =
    RecentlyViewedProduct(
        productId = 1,
        name = "악어다",
        price = 1100,
        imageUrl = "https://image1.com",
        viewedAt = 1,
    )
