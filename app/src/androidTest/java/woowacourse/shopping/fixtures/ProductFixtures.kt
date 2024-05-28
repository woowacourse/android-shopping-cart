package woowacourse.shopping.fixtures

import woowacourse.shopping.domain.entity.Product

fun fakeProduct(
    id: Long = 1,
    price: Int = 1000,
    name: String = "오둥이 $id",
    imageUrl: String = "https://image.com",
): Product {
    return Product(
        id = id,
        price = price,
        name = name,
        imageUrl = imageUrl,
    )
}

fun fakeProducts(size: Int): List<Product> {
    return List(size) {
        fakeProduct(id = (it + 1).toLong(), name = "오둥이 ${it + 1}")
    }
}
