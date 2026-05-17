package woowacourse.shopping.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: String,
    val name: String,
    val price: Long,
    val imageUrl: String,
)
