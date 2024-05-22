package woowacourse.shopping

import woowacourse.shopping.domain.entity.Product

fun product(
    id: Long = 1,
    price: Int = 1000,
    name: String = "오둥이 1",
    imageUrl: String = "https://image.com",
): Product {
    return Product(
        id = id,
        price = price,
        name = name,
        imageUrl = imageUrl,
    )
}

fun products(size: Int): List<Product> {
    return List(size) {
        product(id = (it + 1).toLong(), name = "오둥이 ${it + 1}")
    }
}
