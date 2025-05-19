package woowacourse.shopping.data.dto

data class ProductDto(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val cartQuantity: Int,
)
