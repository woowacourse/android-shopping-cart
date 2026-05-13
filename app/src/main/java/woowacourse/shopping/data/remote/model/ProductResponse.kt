package woowacourse.shopping.data.remote.model

data class ProductResponse(
    val id: Int,
    val name: String,
    val price: Long,
    val imageUrl: String,
)
