package woowacourse.shopping.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseDto(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val price: Int,
)
