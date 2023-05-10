package woowacourse.shopping.domain

fun createProduct(
    url: URL = URL("www.google.com"),
    title: String = "",
    price: Int = 0
): Product = Product(url, title, price)

fun createCartProduct(
    ordinal: Int = 1,
    product: Product = createProduct()
): CartProduct = CartProduct(ordinal, product)

fun createRecentProduct(
    ordinal: Int = 1,
    product: Product = createProduct()
): RecentProduct = RecentProduct(ordinal, product)
