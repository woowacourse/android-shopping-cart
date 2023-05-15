package woowacourse.shopping.domain

import java.time.LocalDateTime

fun createProduct(
    url: URL = URL("www.google.com"),
    title: String = "",
    price: Int = 0
): Product = Product(url, title, price)

fun createCartProduct(
    time: LocalDateTime = LocalDateTime.now(),
    product: Product = createProduct()
): CartProduct = CartProduct(time, product)

fun createRecentProduct(
    time: LocalDateTime = LocalDateTime.now(),
    product: Product = createProduct()
): RecentProduct = RecentProduct(time, product)
