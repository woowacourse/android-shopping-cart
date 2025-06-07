package woowacourse.shopping.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProductContent(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
)
