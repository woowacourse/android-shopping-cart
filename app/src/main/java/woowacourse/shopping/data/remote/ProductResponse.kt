package woowacourse.shopping.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(val content: ProductContent)
