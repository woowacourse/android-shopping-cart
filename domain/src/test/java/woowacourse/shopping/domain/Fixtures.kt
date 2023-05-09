package woowacourse.shopping.domain

fun createProduct(
    url: URL = URL("www.google.com"),
    title: String = "",
    price: Int = 0
): Product {
    return Product(url, title, price)
}
