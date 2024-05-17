package woowacourse.shopping

import woowacourse.shopping.db.Product

fun productsTestFixture(
    count: Int,
    productFixture: (Int) -> Product = { productTestFixture(it) },
): List<Product> = List(count, productFixture)

fun productTestFixture(
    id: Int,
    name: String = "$id name",
    imageUrl: String = "1",
    price: Int = 1,
): Product = Product(id, imageUrl, name, price)
