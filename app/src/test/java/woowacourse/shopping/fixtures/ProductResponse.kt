package woowacourse.shopping.fixtures

import woowacourse.shopping.data.network.model.ProductResponse

fun productResponse(
    id: Long = 1,
    price: Int = 1000,
    name: String = "상품",
    imageUrl: String = "https://image.com"
) = ProductResponse(
    id = id,
    price = price,
    name = name,
    imageUrl = imageUrl
)

fun productResponses(
    vararg productResponse: ProductResponse
) = productResponse.toList()

fun productResponses(
    size: Int,
): List<ProductResponse> = List(size) { productResponse(id = it + 1L) }