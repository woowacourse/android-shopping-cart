package woowacourse.shopping.data.inventory.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponse(
    val products: List<ProductContent>,
)
