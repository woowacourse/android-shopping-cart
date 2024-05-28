package woowacourse.shopping.fixtures

import woowacourse.shopping.remote.model.ProductResponse

fun fakeProductResponse(
    id: Long = 1,
    price: Int = 1000,
    name: String = "상품",
    imageUrl: String = "https://image.com",
) = ProductResponse(
    id = id,
    price = price,
    name = name,
    imageUrl = imageUrl,
)

fun fakeProductResponses(vararg productResponse: ProductResponse) = productResponse.toList()

fun fakeProductResponses(size: Int): List<ProductResponse> = List(size) { fakeProductResponse(id = it + 1L) }
