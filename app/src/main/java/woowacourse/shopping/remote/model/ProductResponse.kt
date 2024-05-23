package woowacourse.shopping.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: Long,
    val price: Int,
    val name: String,
    val imageUrl: String,
)

@Serializable
data class ProductPageResponse(
    val pageNumber: Int,
    val content: List<ProductResponse>,
    val totalPages: Int,
    val pageSize: Int,
    val totalElements: Int,
)
