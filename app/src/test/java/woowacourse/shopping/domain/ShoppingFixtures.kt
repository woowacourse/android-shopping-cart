package woowacourse.shopping.domain

fun product(
    id: Long = 1,
    price: Int = 1000,
    name: String = "콜라",
    imageUrl: String = "https://image.com",
): Product {
    return Product(
        id = id,
        price = price,
        name = name,
        imageUrl = imageUrl,
    )
}
