package woowacourse.shopping.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
)
