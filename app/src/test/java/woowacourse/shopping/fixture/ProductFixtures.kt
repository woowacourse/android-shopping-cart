package woowacourse.shopping.fixture

import woowacourse.shopping.domain.product.Product

val PRODUCT_LUCKY = Product(id = 1, name = "럭키", price = 4000)
val PRODUCT_AIDA = Product(id = 2, name = "아이다", price = 700)
val PRODUCT_SEOLBACK = Product(id = 3, name = "설백", price = 1_000)
val PRODUCT_JUMMA = Product(id = 4, name = "줌마", price = 1_000)
val PRODUCT_JACKSON = Product(id = 5, name = "잭슨", price = 20_000)

val PRODUCTS: List<Product> =
    listOf(PRODUCT_LUCKY, PRODUCT_AIDA, PRODUCT_SEOLBACK, PRODUCT_JUMMA, PRODUCT_JACKSON)
