package woowacourse.shopping.data.inventory.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(val content: ProductContent)
