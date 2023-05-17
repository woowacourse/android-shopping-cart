package woowacourse.shopping.domain

fun makeProductMock(
    url: URL = URL("www.google.com"),
    title: String = "",
    price: Int = 0
): Product = Product(url, title, price)

fun makeCartProductMock(
    amount: Int = 0,
    product: Product = makeProductMock()
): CartProduct = CartProduct(amount, product)

fun makeRecentProductMock(
    ordinal: Int = 1,
    product: Product = makeProductMock()
): RecentProduct = RecentProduct(ordinal, product)
