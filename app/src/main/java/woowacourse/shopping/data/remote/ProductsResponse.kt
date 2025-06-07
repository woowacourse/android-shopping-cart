package woowacourse.shopping.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponse(
    val products: List<ProductContent>,
)
